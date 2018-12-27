package br.gov.pe.reuso.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.gov.pe.reuso.api.model.TipoSolicitacao;
import br.gov.pe.reuso.api.repository.TipoSolicitacaoRepository;
import br.gov.pe.reuso.api.service.exception.TipoSolicitacaoComNomeJaExistenteException;

@Service
public class TipoSolicitacaoService {

	@Autowired
	private TipoSolicitacaoRepository tipoSolicitacaoRepository;
	
	public TipoSolicitacao atualizar(Long id, TipoSolicitacao tipoSolicitacao) {
		TipoSolicitacao tipoSolicitacaoSalvo = buscarTipoSolicitacaoPeloCodigo(id);
		BeanUtils.copyProperties(tipoSolicitacao, tipoSolicitacaoSalvo, "id", "dataCriacao", "usuarioCriacao");
		validarNomeTipoSolicitacaoDuplicado(tipoSolicitacaoSalvo);
		tipoSolicitacaoRepository.save(tipoSolicitacaoSalvo);
		
		return tipoSolicitacaoSalvo;
	}

	public TipoSolicitacao adicionar(TipoSolicitacao tipoSolicitacao) {

		validarNomeTipoSolicitacaoDuplicado(tipoSolicitacao);
		TipoSolicitacao tipoSolicitacaoSalvo = tipoSolicitacaoRepository.save(tipoSolicitacao);
		
		return tipoSolicitacaoSalvo;
	}

	private void validarNomeTipoSolicitacaoDuplicado(TipoSolicitacao tipoSolicitacao) {
		if (tipoSolicitacao.isAlterando()) {
			List<TipoSolicitacao> tipoSolicitacoes = tipoSolicitacaoRepository
					.buscarPorNomeComIdDiferenteDoInformado(tipoSolicitacao.getDescricao(), tipoSolicitacao.getId());
			if (!tipoSolicitacoes.isEmpty()) {
				throw new TipoSolicitacaoComNomeJaExistenteException();
			}
		} else {
			if(tipoSolicitacaoRepository.findByDescricao(tipoSolicitacao.getDescricao()).isPresent()) {
				throw new TipoSolicitacaoComNomeJaExistenteException();
			}
		}
	}

	private TipoSolicitacao buscarTipoSolicitacaoPeloCodigo(Long id) {
		TipoSolicitacao tipoSolicitacaoSalvo = tipoSolicitacaoRepository.findById(id).
				orElseThrow(() -> new EmptyResultDataAccessException(1));
		return tipoSolicitacaoSalvo;
	}
}
