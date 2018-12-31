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
import br.gov.pe.reuso.api.model.Solicitacao;
import br.gov.pe.reuso.api.repository.SolicitacaoRepository;
import br.gov.pe.reuso.api.service.SolicitacaoService;
import br.gov.pe.reuso.api.service.exception.SolicitacaoInexistenteException;


@RestController
@RequestMapping("/solicitacoes")
public class SolicitacaoResource {
	
	@Autowired
	private SolicitacaoRepository solicitacaoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private SolicitacaoService solicitacaoService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	public List<Solicitacao> listar() {
		return solicitacaoRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Solicitacao> criar(@Valid @RequestBody Solicitacao solicitacao, HttpServletResponse response) {
		Solicitacao solicitacaoSalvo = solicitacaoService.adicionar(solicitacao);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, solicitacaoSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(solicitacaoSalvo);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Solicitacao> buscarPeloId(@PathVariable Long id) {
		Optional<Solicitacao> solicitacaoOptional = solicitacaoRepository.findById(id);
		
		return solicitacaoOptional.isPresent() ? ResponseEntity.ok(solicitacaoOptional.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		solicitacaoRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Solicitacao> atualizar(@PathVariable Long id, @Valid @RequestBody Solicitacao solicitacao) {
		Solicitacao solicitacaoSalvo = solicitacaoService.atualizar(id, solicitacao);
		
		return ResponseEntity.ok(solicitacaoSalvo);
	}
	
	@ExceptionHandler(SolicitacaoInexistenteException.class)
	public ResponseEntity<Object> handleFonteComNomeJaExistenteException(SolicitacaoInexistenteException ex) {
		String mensagemSolicitacao = messageSource.getMessage("solicitacao.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemSolicitacao, mensagemDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
	}
}
