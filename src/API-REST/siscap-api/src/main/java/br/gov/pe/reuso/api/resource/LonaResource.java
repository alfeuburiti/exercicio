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
import br.gov.pe.reuso.api.model.Lona;
import br.gov.pe.reuso.api.repository.LonaRepository;
import br.gov.pe.reuso.api.service.LonaService;
import br.gov.pe.reuso.api.service.exception.LonaInexistenteException;

@RestController
@RequestMapping("/lonas")
public class LonaResource {

	@Autowired
	private LonaRepository lonaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private LonaService lonaService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	public List<Lona> listar() {
		return lonaRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Lona> criar(@Valid @RequestBody Lona lona, HttpServletResponse response) {
		Lona lonaSalvo = lonaService.adicionar(lona);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lonaSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lonaSalvo);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Lona> buscarPeloId(@PathVariable Long id) {
		Optional<Lona> lonaOptional = lonaRepository.findById(id);
		
		return lonaOptional.isPresent() ? ResponseEntity.ok(lonaOptional.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		lonaRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Lona> atualizar(@PathVariable Long id, @Valid @RequestBody Lona lona) {
		Lona lonaSalvo = lonaService.atualizar(id, lona);
		
		return ResponseEntity.ok(lonaSalvo);
	}
	
	@ExceptionHandler(LonaInexistenteException.class)
	public ResponseEntity<Object> handleFonteComNomeJaExistenteException(LonaInexistenteException ex) {
		String mensagemLona = messageSource.getMessage("lona.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemLona, mensagemDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
	}
}
