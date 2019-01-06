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
import br.gov.pe.reuso.api.model.Setor;
import br.gov.pe.reuso.api.repository.SetorRepository;
import br.gov.pe.reuso.api.service.SetorService;
import br.gov.pe.reuso.api.service.exception.SetorComAreaJaExistenteException;

@RestController
@RequestMapping("/setores")
public class SetorResource {

	@Autowired
	private SetorRepository setorRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private SetorService setorService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	public List<Setor> listar() {
		return setorRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Setor> criar(@Valid @RequestBody Setor setor, HttpServletResponse response) {
		Setor setorSalvo = setorService.adicionar(setor);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, setorSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(setorSalvo);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Setor> buscarPeloId(@PathVariable Long id) {
		Optional<Setor> setorOptional = setorRepository.findById(id);
		
		return setorOptional.isPresent() ? ResponseEntity.ok(setorOptional.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		setorRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Setor> atualizar(@PathVariable Long id, @Valid @RequestBody Setor setor) {
		Setor setorSalvo = setorService.atualizar(id, setor);
		
		return ResponseEntity.ok(setorSalvo);
	}
	
	@ExceptionHandler(SetorComAreaJaExistenteException.class)
	public ResponseEntity<Object> handleFonteComNomeJaExistenteException(SetorComAreaJaExistenteException ex) {
		String mensagemSetor = messageSource.getMessage("setor.area-ja-existente", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemSetor, mensagemDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
	}
}
