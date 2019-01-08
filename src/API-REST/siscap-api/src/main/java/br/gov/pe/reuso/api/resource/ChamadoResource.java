package br.gov.pe.reuso.api.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.gov.pe.reuso.api.event.RecursoCriadoEvent;
import br.gov.pe.reuso.api.exceptionhandler.SedecExceptionHandler.Erro;
import br.gov.pe.reuso.api.model.Chamado;
import br.gov.pe.reuso.api.repository.ChamadoRepository;
import br.gov.pe.reuso.api.service.ChamadoService;
import br.gov.pe.reuso.api.service.exception.ChamadoInexistenteException;

@RestController
@RequestMapping("/chamados")
public class ChamadoResource {
	

	@Autowired
	private ChamadoRepository chamadoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private ChamadoService chamadoService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	public List<Chamado> listar() {
		return chamadoRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Chamado> criar(@Valid @RequestBody Chamado chamado, HttpServletResponse response) {
		Chamado chamadoSalvo = chamadoService.adicionar(chamado);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, chamadoSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(chamadoSalvo);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Chamado> buscarPeloId(@PathVariable Long id) {
		Optional<Chamado> chamadoOptional = chamadoRepository.findById(id);
		
		return chamadoOptional.isPresent() ? ResponseEntity.ok(chamadoOptional.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		chamadoRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Chamado> atualizar(@PathVariable Long id, @Valid @RequestBody Chamado chamado) {
		Chamado chamadoSalvo = chamadoService.atualizar(id, chamado);
		
		return ResponseEntity.ok(chamadoSalvo);
	}
	
	@ExceptionHandler(ChamadoInexistenteException.class)
	public ResponseEntity<Object> handleFonteComNomeJaExistenteException(ChamadoInexistenteException ex) {
		String mensagemChamado = messageSource.getMessage("chamado.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemChamado, mensagemDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
	}
}
