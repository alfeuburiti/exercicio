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
import br.gov.pe.reuso.api.model.TipoSolicitacao;
import br.gov.pe.reuso.api.repository.TipoSolicitacaoRepository;
import br.gov.pe.reuso.api.service.TipoSolicitacaoService;
import br.gov.pe.reuso.api.service.exception.TipoSolicitacaoComDescricaoJaExistenteException;


@RestController
@RequestMapping("/tipo-solicitacoes")
public class TipoSolicitacaoResource {
	
	@Autowired
	private TipoSolicitacaoRepository tipoSolicitacaoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private TipoSolicitacaoService tipoSolicitacaoService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	public List<TipoSolicitacao> listar() {
		return tipoSolicitacaoRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<TipoSolicitacao> criar(@Valid @RequestBody TipoSolicitacao tipoSolicitacao, HttpServletResponse response) {
		TipoSolicitacao tipoSolicitacaoSalvo = tipoSolicitacaoService.adicionar(tipoSolicitacao);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, tipoSolicitacaoSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(tipoSolicitacaoSalvo);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TipoSolicitacao> buscarPeloId(@PathVariable Long id) {
		Optional<TipoSolicitacao> tipoSolicitacaoOptional = tipoSolicitacaoRepository.findById(id);
		
		return tipoSolicitacaoOptional.isPresent() ? ResponseEntity.ok(tipoSolicitacaoOptional.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		tipoSolicitacaoRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TipoSolicitacao> atualizar(@PathVariable Long id, @Valid @RequestBody TipoSolicitacao tipoSolicitacao) {
		TipoSolicitacao tipoSolicitacaoSalvo = tipoSolicitacaoService.atualizar(id, tipoSolicitacao);
		
		return ResponseEntity.ok(tipoSolicitacaoSalvo);
	}
	
	@ExceptionHandler(TipoSolicitacaoComDescricaoJaExistenteException.class)
	public ResponseEntity<Object> handleFonteComNomeJaExistenteException(TipoSolicitacaoComDescricaoJaExistenteException ex) {
		String mensagemTipoSolicitacao = messageSource.getMessage("tipoSolicitacao.nome-ja-existente", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemTipoSolicitacao, mensagemDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
	}

}
