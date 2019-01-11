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
import br.gov.pe.reuso.api.model.ProcessoStatus;
import br.gov.pe.reuso.api.repository.ProcessoStatusRepository;
import br.gov.pe.reuso.api.service.ProcessoStatusService;
import br.gov.pe.reuso.api.service.exception.ProcessoStatusComDescricaoJaExistenteException;

@RestController
@RequestMapping("/processo-statuses")
public class ProcessoStatusResource {

	@Autowired
	private ProcessoStatusRepository processoStatusRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private ProcessoStatusService processoStatusService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	public List<ProcessoStatus> listar() {
		return processoStatusRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<ProcessoStatus> criar(@Valid @RequestBody ProcessoStatus processoStatus, HttpServletResponse response) {
		ProcessoStatus processoStatusSalvo = processoStatusService.adicionar(processoStatus);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, processoStatusSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(processoStatusSalvo);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProcessoStatus> buscarPeloId(@PathVariable Long id) {
		Optional<ProcessoStatus> processoStatusOptional = processoStatusRepository.findById(id);
		
		return processoStatusOptional.isPresent() ? ResponseEntity.ok(processoStatusOptional.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		processoStatusRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProcessoStatus> atualizar(@PathVariable Long id, @Valid @RequestBody ProcessoStatus processoStatus) {
		ProcessoStatus processoStatusSalvo = processoStatusService.atualizar(id, processoStatus);
		
		return ResponseEntity.ok(processoStatusSalvo);
	}
	
	@ExceptionHandler(ProcessoStatusComDescricaoJaExistenteException.class)
	public ResponseEntity<Object> handleFonteComNomeJaExistenteException(ProcessoStatusComDescricaoJaExistenteException ex) {
		String mensagemProcessoStatus = messageSource.getMessage("processoStatus.descricao-ja-existente", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemProcessoStatus, mensagemDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
	}
}
