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
import br.gov.pe.reuso.api.model.Bairro;
import br.gov.pe.reuso.api.repository.BairroRepository;
import br.gov.pe.reuso.api.service.BairroService;
import br.gov.pe.reuso.api.service.exception.BairroComDescricaoJaExistenteException;


@RestController
@RequestMapping("/bairros")
public class BairroResource {

	@Autowired
	private BairroRepository bairroRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private BairroService bairroService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	public List<Bairro> listar() {
		return bairroRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Bairro> criar(@Valid @RequestBody Bairro bairro, HttpServletResponse response) {
		Bairro bairroSalvo = bairroService.adicionar(bairro);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, bairroSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(bairroSalvo);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Bairro> buscarPeloId(@PathVariable Long id) {
		Optional<Bairro> bairroOptional = bairroRepository.findById(id);
		
		return bairroOptional.isPresent() ? ResponseEntity.ok(bairroOptional.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		bairroRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Bairro> atualizar(@PathVariable Long id, @Valid @RequestBody Bairro bairro) {
		Bairro bairroSalvo = bairroService.atualizar(id, bairro);
		
		return ResponseEntity.ok(bairroSalvo);
	}
	
	@ExceptionHandler(BairroComDescricaoJaExistenteException.class)
	public ResponseEntity<Object> handleFonteComNomeJaExistenteException(BairroComDescricaoJaExistenteException ex) {
		String mensagemBairro = messageSource.getMessage("bairro.nome-ja-existente", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemBairro, mensagemDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
	}
}
