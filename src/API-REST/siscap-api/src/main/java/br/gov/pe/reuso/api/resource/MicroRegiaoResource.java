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
import br.gov.pe.reuso.api.model.MicroRegiao;
import br.gov.pe.reuso.api.repository.MicroRegiaoRepository;
import br.gov.pe.reuso.api.service.MicroRegiaoService;
import br.gov.pe.reuso.api.service.exception.MicroRegiaoComDescricaoJaExistenteException;

@RestController
@RequestMapping("/micro-regioes")
public class MicroRegiaoResource {
	
	@Autowired
	private MicroRegiaoRepository microRegiaoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private MicroRegiaoService microRegiaoService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	public List<MicroRegiao> listar() {
		return microRegiaoRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<MicroRegiao> criar(@Valid @RequestBody MicroRegiao microRegiao, HttpServletResponse response) {
		MicroRegiao microRegiaoSalvo = microRegiaoService.adicionar(microRegiao);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, microRegiaoSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(microRegiaoSalvo);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<MicroRegiao> buscarPeloId(@PathVariable Long id) {
		Optional<MicroRegiao> microRegiaoOptional = microRegiaoRepository.findById(id);
		
		return microRegiaoOptional.isPresent() ? ResponseEntity.ok(microRegiaoOptional.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		microRegiaoRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<MicroRegiao> atualizar(@PathVariable Long id, @Valid @RequestBody MicroRegiao microRegiao) {
		MicroRegiao microRegiaoSalvo = microRegiaoService.atualizar(id, microRegiao);
		
		return ResponseEntity.ok(microRegiaoSalvo);
	}
	
	@ExceptionHandler(MicroRegiaoComDescricaoJaExistenteException.class)
	public ResponseEntity<Object> handleFonteComNomeJaExistenteException(MicroRegiaoComDescricaoJaExistenteException ex) {
		String mensagemMicroRegiao = messageSource.getMessage("microRegiao.nome-ja-existente", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemMicroRegiao, mensagemDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
	}

}
