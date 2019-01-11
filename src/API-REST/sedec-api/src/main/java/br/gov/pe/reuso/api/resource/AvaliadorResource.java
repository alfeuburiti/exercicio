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
import br.gov.pe.reuso.api.model.Avaliador;
import br.gov.pe.reuso.api.repository.AvaliadorRepository;
import br.gov.pe.reuso.api.service.AvaliadorService;
import br.gov.pe.reuso.api.service.exception.AvaliadorComEspecialidadeJaExistenteException;

@RestController
@RequestMapping("/avaliadores")
public class AvaliadorResource {

	@Autowired
	private AvaliadorRepository avaliadorRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private AvaliadorService avaliadorService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	public List<Avaliador> listar() {
		return avaliadorRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Avaliador> criar(@Valid @RequestBody Avaliador avaliador, HttpServletResponse response) {
		Avaliador avaliadorSalvo = avaliadorService.adicionar(avaliador);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, avaliadorSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(avaliadorSalvo);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Avaliador> buscarPeloId(@PathVariable Long id) {
		Optional<Avaliador> avaliadorOptional = avaliadorRepository.findById(id);
		
		return avaliadorOptional.isPresent() ? ResponseEntity.ok(avaliadorOptional.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		avaliadorRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Avaliador> atualizar(@PathVariable Long id, @Valid @RequestBody Avaliador avaliador) {
		Avaliador avaliadorSalvo = avaliadorService.atualizar(id, avaliador);
		
		return ResponseEntity.ok(avaliadorSalvo);
	}
	
	@ExceptionHandler(AvaliadorComEspecialidadeJaExistenteException.class)
	public ResponseEntity<Object> handleFonteComNomeJaExistenteException(AvaliadorComEspecialidadeJaExistenteException ex) {
		String mensagemAvaliador = messageSource.getMessage("avaliador.especialidade-ja-existente", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemAvaliador, mensagemDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
	}
}
