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
import br.gov.pe.reuso.api.model.Regional;
import br.gov.pe.reuso.api.repository.RegionalRepository;
import br.gov.pe.reuso.api.service.RegionalService;
import br.gov.pe.reuso.api.service.exception.RegionalComNomeJaExistenteException;


@RestController
@RequestMapping("/regionais")
public class RegionalResource {

	@Autowired
	private RegionalRepository regionalRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private RegionalService regionalService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	public List<Regional> listar() {
		return regionalRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Regional> criar(@Valid @RequestBody Regional regional, HttpServletResponse response) {
		Regional regionalSalvo = regionalService.adicionar(regional);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, regionalSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(regionalSalvo);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Regional> buscarPeloId(@PathVariable Long id) {
		Optional<Regional> regionalOptional = regionalRepository.findById(id);
		
		return regionalOptional.isPresent() ? ResponseEntity.ok(regionalOptional.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		regionalRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Regional> atualizar(@PathVariable Long id, @Valid @RequestBody Regional regional) {
		Regional regionalSalvo = regionalService.atualizar(id, regional);
		
		return ResponseEntity.ok(regionalSalvo);
	}
	
	@ExceptionHandler(RegionalComNomeJaExistenteException.class)
	public ResponseEntity<Object> handleFonteComNomeJaExistenteException(RegionalComNomeJaExistenteException ex) {
		String mensagemRegional = messageSource.getMessage("regional.nome-ja-existente", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemRegional, mensagemDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
	}
	
}
