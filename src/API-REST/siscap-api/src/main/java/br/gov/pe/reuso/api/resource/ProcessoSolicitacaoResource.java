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
import br.gov.pe.reuso.api.model.ProcessoSolicitacao;
import br.gov.pe.reuso.api.repository.ProcessoSolicitacaoRepository;
import br.gov.pe.reuso.api.service.ProcessoSolicitacaoService;

@RestController
@RequestMapping("/processo-solicitacoes")
public class ProcessoSolicitacaoResource {

	@Autowired
	private ProcessoSolicitacaoRepository processoProcessoSolicitacaoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private ProcessoSolicitacaoService processoProcessoSolicitacaoService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	public List<ProcessoSolicitacao> listar() {
		return processoProcessoSolicitacaoRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<ProcessoSolicitacao> criar(@Valid @RequestBody ProcessoSolicitacao processoProcessoSolicitacao, HttpServletResponse response) {
		
		ProcessoSolicitacao processoProcessoSolicitacaoSalvo = processoProcessoSolicitacaoService.adicionar(processoProcessoSolicitacao);
		return ResponseEntity.status(HttpStatus.CREATED).body(processoProcessoSolicitacaoSalvo);
	}
	

	

	
}
