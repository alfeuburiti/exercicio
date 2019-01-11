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
import br.gov.pe.reuso.api.model.Localidade;
import br.gov.pe.reuso.api.repository.LocalidadeRepository;
import br.gov.pe.reuso.api.service.LocalidadeService;
import br.gov.pe.reuso.api.service.exception.LocalidadeComDescricaoJaExistenteException;

@RestController
@RequestMapping("/localidades")
public class LocalidadeResource {
	
	@Autowired
	private LocalidadeRepository localidadeRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private LocalidadeService localidadeService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	public List<Localidade> listar() {
		return localidadeRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Localidade> criar(@Valid @RequestBody Localidade localidade, HttpServletResponse response) {
		Localidade localidadeSalvo = localidadeService.adicionar(localidade);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, localidadeSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(localidadeSalvo);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Localidade> buscarPeloId(@PathVariable Long id) {
		Optional<Localidade> localidadeOptional = localidadeRepository.findById(id);
		
		return localidadeOptional.isPresent() ? ResponseEntity.ok(localidadeOptional.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		localidadeRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Localidade> atualizar(@PathVariable Long id, @Valid @RequestBody Localidade localidade) {
		Localidade localidadeSalvo = localidadeService.atualizar(id, localidade);
		
		return ResponseEntity.ok(localidadeSalvo);
	}
	
	@ExceptionHandler(LocalidadeComDescricaoJaExistenteException.class)
	public ResponseEntity<Object> handleFonteComNomeJaExistenteException(LocalidadeComDescricaoJaExistenteException ex) {
		String mensagemLocalidade = messageSource.getMessage("localidade.descricao-ja-existente", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemLocalidade, mensagemDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
	}
}
