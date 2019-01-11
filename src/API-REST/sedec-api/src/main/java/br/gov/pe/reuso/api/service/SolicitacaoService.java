package br.gov.pe.reuso.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.gov.pe.reuso.api.model.Solicitacao;
import br.gov.pe.reuso.api.repository.SolicitacaoRepository;

@Service
public class SolicitacaoService {
	
	@Autowired
	private SolicitacaoRepository solicitacaoRepository;
	
	public Solicitacao atualizar(Long id, Solicitacao solicitacao) {
		Solicitacao solicitacaoSalvo = buscarSolicitacaoPeloCodigo(id);
		BeanUtils.copyProperties(solicitacao, solicitacaoSalvo, "id", "dataCriacao", "usuarioCriacao");
		solicitacaoRepository.save(solicitacaoSalvo);
		
		return solicitacaoSalvo;
	}
	
	public Solicitacao adicionar(Solicitacao solicitacao) {

		Solicitacao solicitacaoSalvo = solicitacaoRepository.save(solicitacao);
		return solicitacaoSalvo;
	}

	private Solicitacao buscarSolicitacaoPeloCodigo(Long id) {
		Solicitacao solicitacaoSalvo = solicitacaoRepository.findById(id).
				orElseThrow(() -> new EmptyResultDataAccessException(1));
		return solicitacaoSalvo;
	}
}
