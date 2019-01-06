package br.gov.pe.reuso.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.gov.pe.reuso.api.model.ProcessoSolicitacao;
import br.gov.pe.reuso.api.repository.ProcessoSolicitacaoRepository;

@Service
public class ProcessoSolicitacaoService {

	@Autowired
	private ProcessoSolicitacaoRepository processoSolicitacaoRepository;
	
	public ProcessoSolicitacao atualizar(Long id, ProcessoSolicitacao processoSolicitacao) {
		ProcessoSolicitacao processoSolicitacaoSalvo = buscarProcessoSolicitacaoPeloCodigo(id);
		BeanUtils.copyProperties(processoSolicitacao, processoSolicitacaoSalvo, "id", "dataCriacao", "usuarioCriacao");
		processoSolicitacaoRepository.save(processoSolicitacaoSalvo);
		
		return processoSolicitacaoSalvo;
	}
	
	public ProcessoSolicitacao adicionar(ProcessoSolicitacao processoSolicitacao) {

		ProcessoSolicitacao processoSolicitacaoSalvo = processoSolicitacaoRepository.save(processoSolicitacao);
		return processoSolicitacaoSalvo;
	}

	private ProcessoSolicitacao buscarProcessoSolicitacaoPeloCodigo(Long id) {
		ProcessoSolicitacao processoSolicitacaoSalvo = processoSolicitacaoRepository.findById(id).
				orElseThrow(() -> new EmptyResultDataAccessException(1));
		return processoSolicitacaoSalvo;
	}
}
