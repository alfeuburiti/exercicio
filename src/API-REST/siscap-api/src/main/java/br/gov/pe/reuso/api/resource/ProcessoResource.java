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
import br.gov.pe.reuso.api.model.Processo;
import br.gov.pe.reuso.api.repository.ProcessoRepository;
import br.gov.pe.reuso.api.service.ProcessoService;
import br.gov.pe.reuso.api.service.exception.ProcessoComNumeroJaExistenteException;

@RestController
@RequestMapping("/processos")
public class ProcessoResource {

	@Autowired
	private ProcessoRepository processoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private ProcessoService processoService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	public List<Processo> listar() {
		return processoRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Processo> criar(@Valid @RequestBody Processo processo, HttpServletResponse response) {
		Processo processoSalvo = processoService.adicionar(processo);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, processoSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(processoSalvo);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Processo> buscarPeloId(@PathVariable Long id) {
		Optional<Processo> processoOptional = processoRepository.findById(id);
		
		return processoOptional.isPresent() ? ResponseEntity.ok(processoOptional.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		processoRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Processo> atualizar(@PathVariable Long id, @Valid @RequestBody Processo processo) {
		Processo processoSalvo = processoService.atualizar(id, processo);
		
		return ResponseEntity.ok(processoSalvo);
	}
	
	@ExceptionHandler(ProcessoComNumeroJaExistenteException.class)
	public ResponseEntity<Object> handleFonteComNomeJaExistenteException(ProcessoComNumeroJaExistenteException ex) {
		String mensagemProcesso = messageSource.getMessage("processo.numero-ja-existente", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemProcesso, mensagemDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
	}
}
