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
import br.gov.pe.reuso.api.model.Rpa;
import br.gov.pe.reuso.api.repository.RpaRepository;
import br.gov.pe.reuso.api.service.RpaService;
import br.gov.pe.reuso.api.service.exception.RpaComNomeJaExistenteException;


@RestController
@RequestMapping("/rpas")
public class RpaResource {

	@Autowired
	private RpaRepository rpaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private RpaService rpaService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	public List<Rpa> listar() {
		return rpaRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Rpa> criar(@Valid @RequestBody Rpa rpa, HttpServletResponse response) {
		Rpa rpaSalvo = rpaService.adicionar(rpa);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, rpaSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(rpaSalvo);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Rpa> buscarPeloId(@PathVariable Long id) {
		Optional<Rpa> rpaOptional = rpaRepository.findById(id);
		
		return rpaOptional.isPresent() ? ResponseEntity.ok(rpaOptional.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		rpaRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Rpa> atualizar(@PathVariable Long id, @Valid @RequestBody Rpa rpa) {
		Rpa rpaSalvo = rpaService.atualizar(id, rpa);
		
		return ResponseEntity.ok(rpaSalvo);
	}
	
	@ExceptionHandler(RpaComNomeJaExistenteException.class)
	public ResponseEntity<Object> handleFonteComNomeJaExistenteException(RpaComNomeJaExistenteException ex) {
		String mensagemRpa = messageSource.getMessage("rpa.nome-ja-existente", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemRpa, mensagemDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
	}
}
