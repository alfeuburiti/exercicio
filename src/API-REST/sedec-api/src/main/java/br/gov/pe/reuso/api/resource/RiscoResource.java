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
import br.gov.pe.reuso.api.model.Risco;
import br.gov.pe.reuso.api.repository.RiscoRepository;
import br.gov.pe.reuso.api.service.RiscoService;
import br.gov.pe.reuso.api.service.exception.RiscoComCategoriaJaExistenteException;

@RestController
@RequestMapping("/riscos")
public class RiscoResource {

	@Autowired
	private RiscoRepository riscoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private RiscoService riscoService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	public List<Risco> listar() {
		return riscoRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Risco> criar(@Valid @RequestBody Risco risco, HttpServletResponse response) {
		Risco riscoSalvo = riscoService.adicionar(risco);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, riscoSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(riscoSalvo);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Risco> buscarPeloId(@PathVariable Long id) {
		Optional<Risco> riscoOptional = riscoRepository.findById(id);
		
		return riscoOptional.isPresent() ? ResponseEntity.ok(riscoOptional.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		riscoRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Risco> atualizar(@PathVariable Long id, @Valid @RequestBody Risco risco) {
		Risco riscoSalvo = riscoService.atualizar(id, risco);
		
		return ResponseEntity.ok(riscoSalvo);
	}
	
	@ExceptionHandler(RiscoComCategoriaJaExistenteException.class)
	public ResponseEntity<Object> handleFonteComNomeJaExistenteException(RiscoComCategoriaJaExistenteException ex) {
		String mensagemRisco = messageSource.getMessage("risco.categoria-ja-existente", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemRisco, mensagemDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
	}
	
}
