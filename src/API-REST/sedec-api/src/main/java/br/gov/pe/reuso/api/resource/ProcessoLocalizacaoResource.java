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
import br.gov.pe.reuso.api.model.ProcessoLocalizacao;
import br.gov.pe.reuso.api.repository.ProcessoLocalizacaoRepository;
import br.gov.pe.reuso.api.service.ProcessoLocalizacaoService;
import br.gov.pe.reuso.api.service.exception.ProcessoLocalizacaoComDescricaoJaExistenteException;

@RestController
@RequestMapping("/processo-localizacoes")
public class ProcessoLocalizacaoResource {

	@Autowired
	private ProcessoLocalizacaoRepository processoLocalizacaoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private ProcessoLocalizacaoService processoLocalizacaoService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	public List<ProcessoLocalizacao> listar() {
		return processoLocalizacaoRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<ProcessoLocalizacao> criar(@Valid @RequestBody ProcessoLocalizacao processoLocalizacao, HttpServletResponse response) {
		ProcessoLocalizacao processoLocalizacaoSalvo = processoLocalizacaoService.adicionar(processoLocalizacao);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, processoLocalizacaoSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(processoLocalizacaoSalvo);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProcessoLocalizacao> buscarPeloId(@PathVariable Long id) {
		Optional<ProcessoLocalizacao> processoLocalizacaoOptional = processoLocalizacaoRepository.findById(id);
		
		return processoLocalizacaoOptional.isPresent() ? ResponseEntity.ok(processoLocalizacaoOptional.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		processoLocalizacaoRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProcessoLocalizacao> atualizar(@PathVariable Long id, @Valid @RequestBody ProcessoLocalizacao processoLocalizacao) {
		ProcessoLocalizacao processoLocalizacaoSalvo = processoLocalizacaoService.atualizar(id, processoLocalizacao);
		
		return ResponseEntity.ok(processoLocalizacaoSalvo);
	}
	
	@ExceptionHandler(ProcessoLocalizacaoComDescricaoJaExistenteException.class)
	public ResponseEntity<Object> handleFonteComNomeJaExistenteException(ProcessoLocalizacaoComDescricaoJaExistenteException ex) {
		String mensagemProcessoLocalizacao = messageSource.getMessage("processoLocalizacao.descricao-ja-existente", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemProcessoLocalizacao, mensagemDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
	}
}
