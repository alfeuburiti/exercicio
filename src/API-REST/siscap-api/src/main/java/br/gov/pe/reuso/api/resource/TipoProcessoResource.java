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
import br.gov.pe.reuso.api.model.TipoProcesso;
import br.gov.pe.reuso.api.repository.TipoProcessoRepository;
import br.gov.pe.reuso.api.service.TipoProcessoService;
import br.gov.pe.reuso.api.service.exception.TipoProcessoComNomeJaExistenteException;


@RestController
@RequestMapping("/tipo-processos")

public class TipoProcessoResource {

	@Autowired
	private TipoProcessoRepository tipoProcessoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private TipoProcessoService tipoProcessoService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	public List<TipoProcesso> listar() {
		return tipoProcessoRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<TipoProcesso> criar(@Valid @RequestBody TipoProcesso tipoProcesso, HttpServletResponse response) {
		TipoProcesso tipoProcessoSalvo = tipoProcessoService.adicionar(tipoProcesso);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, tipoProcessoSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(tipoProcessoSalvo);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TipoProcesso> buscarPeloId(@PathVariable Long id) {
		Optional<TipoProcesso> tipoProcessoOptional = tipoProcessoRepository.findById(id);
		
		return tipoProcessoOptional.isPresent() ? ResponseEntity.ok(tipoProcessoOptional.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		tipoProcessoRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TipoProcesso> atualizar(@PathVariable Long id, @Valid @RequestBody TipoProcesso tipoProcesso) {
		TipoProcesso tipoProcessoSalvo = tipoProcessoService.atualizar(id, tipoProcesso);
		
		return ResponseEntity.ok(tipoProcessoSalvo);
	}
	
	@ExceptionHandler(TipoProcessoComNomeJaExistenteException.class)
	public ResponseEntity<Object> handleFonteComNomeJaExistenteException(TipoProcessoComNomeJaExistenteException ex) {
		String mensagemTipoProcesso = messageSource.getMessage("tipoProcesso.nome-ja-existente", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemTipoProcesso, mensagemDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
	}
	
}
