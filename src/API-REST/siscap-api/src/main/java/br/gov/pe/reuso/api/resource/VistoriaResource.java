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
import br.gov.pe.reuso.api.model.Vistoria;
import br.gov.pe.reuso.api.repository.VistoriaRepository;
import br.gov.pe.reuso.api.service.VistoriaService;
import br.gov.pe.reuso.api.service.exception.VistoriaInexistenteException;

@RestController
@RequestMapping("/vistorias")
public class VistoriaResource {

	@Autowired
	private VistoriaRepository vistoriaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private VistoriaService vistoriaService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	public List<Vistoria> listar() {
		return vistoriaRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Vistoria> criar(@Valid @RequestBody Vistoria vistoria, HttpServletResponse response) {
		Vistoria vistoriaSalvo = vistoriaService.adicionar(vistoria);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, vistoriaSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(vistoriaSalvo);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Vistoria> buscarPeloId(@PathVariable Long id) {
		Optional<Vistoria> vistoriaOptional = vistoriaRepository.findById(id);
		
		return vistoriaOptional.isPresent() ? ResponseEntity.ok(vistoriaOptional.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		vistoriaRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Vistoria> atualizar(@PathVariable Long id, @Valid @RequestBody Vistoria vistoria) {
		Vistoria vistoriaSalvo = vistoriaService.atualizar(id, vistoria);
		
		return ResponseEntity.ok(vistoriaSalvo);
	}
	
	@ExceptionHandler(VistoriaInexistenteException.class)
	public ResponseEntity<Object> handleFonteComNomeJaExistenteException(VistoriaInexistenteException ex) {
		String mensagemVistoria = messageSource.getMessage("vistoria.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemVistoria, mensagemDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
	}
}
