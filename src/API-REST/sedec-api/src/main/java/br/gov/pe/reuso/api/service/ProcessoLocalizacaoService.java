package br.gov.pe.reuso.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.gov.pe.reuso.api.model.ProcessoLocalizacao;
import br.gov.pe.reuso.api.repository.ProcessoLocalizacaoRepository;
import br.gov.pe.reuso.api.service.exception.ProcessoLocalizacaoComDescricaoJaExistenteException;

@Service
public class ProcessoLocalizacaoService {

	@Autowired
	private ProcessoLocalizacaoRepository processoLocalizacaoRepository;
	
	public ProcessoLocalizacao atualizar(Long id, ProcessoLocalizacao processoLocalizacao) {
		ProcessoLocalizacao processoLocalizacaoSalvo = buscarProcessoLocalizacaoPeloCodigo(id);
		BeanUtils.copyProperties(processoLocalizacao, processoLocalizacaoSalvo, "id", "dataCriacao", "usuarioCriacao");
		validarDescricaoProcessoLocalizacaoDuplicado(processoLocalizacaoSalvo);
		processoLocalizacaoRepository.save(processoLocalizacaoSalvo);
		
		return processoLocalizacaoSalvo;
	}
	
	public ProcessoLocalizacao adicionar(ProcessoLocalizacao processoLocalizacao) {

		validarDescricaoProcessoLocalizacaoDuplicado(processoLocalizacao);
		ProcessoLocalizacao processoLocalizacaoSalvo = processoLocalizacaoRepository.save(processoLocalizacao);
		
		return processoLocalizacaoSalvo;
	}

	private void validarDescricaoProcessoLocalizacaoDuplicado(ProcessoLocalizacao processoLocalizacao) {
		if (processoLocalizacao.isAlterando()) {
			List<ProcessoLocalizacao> processoLocalizacaos = processoLocalizacaoRepository
					.buscarPorDescricaoComIdDiferenteDoInformado(processoLocalizacao.getDescricao(), processoLocalizacao.getId());
			if (!processoLocalizacaos.isEmpty()) {
				throw new ProcessoLocalizacaoComDescricaoJaExistenteException();
			}
		} else {
			if(processoLocalizacaoRepository.findByDescricao(processoLocalizacao.getDescricao()).isPresent()) {
				throw new ProcessoLocalizacaoComDescricaoJaExistenteException();
			}
		}
	}

	private ProcessoLocalizacao buscarProcessoLocalizacaoPeloCodigo(Long id) {
		ProcessoLocalizacao processoLocalizacaoSalvo = processoLocalizacaoRepository.findById(id).
				orElseThrow(() -> new EmptyResultDataAccessException(1));
		return processoLocalizacaoSalvo;
	}
}
