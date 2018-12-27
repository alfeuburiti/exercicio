package br.gov.pe.reuso.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.gov.pe.reuso.api.model.TipoProcesso;
import br.gov.pe.reuso.api.repository.TipoProcessoRepository;
import br.gov.pe.reuso.api.service.exception.TipoProcessoComNomeJaExistenteException;

@Service
public class TipoProcessoService {

	@Autowired
	private TipoProcessoRepository tipoProcessoRepository;
	
	public TipoProcesso atualizar(Long id, TipoProcesso tipoProcesso) {
		TipoProcesso tipoProcessoSalvo = buscarTipoProcessoPeloCodigo(id);
		BeanUtils.copyProperties(tipoProcesso, tipoProcessoSalvo, "id", "dataCriacao", "usuarioCriacao");
		validarNomeTipoProcessoDuplicado(tipoProcessoSalvo);
		tipoProcessoRepository.save(tipoProcessoSalvo);
		
		return tipoProcessoSalvo;
	}

	public TipoProcesso adicionar(TipoProcesso tipoProcesso) {

		validarNomeTipoProcessoDuplicado(tipoProcesso);
		TipoProcesso tipoProcessoSalvo = tipoProcessoRepository.save(tipoProcesso);
		
		return tipoProcessoSalvo;
	}

	private void validarNomeTipoProcessoDuplicado(TipoProcesso tipoProcesso) {
		if (tipoProcesso.isAlterando()) {
			List<TipoProcesso> tipoProcessos = tipoProcessoRepository
					.buscarPorNomeComIdDiferenteDoInformado(tipoProcesso.getDescricao(), tipoProcesso.getId());
			if (!tipoProcessos.isEmpty()) {
				throw new TipoProcessoComNomeJaExistenteException();
			}
		} else {
			if(tipoProcessoRepository.findByDescricao(tipoProcesso.getDescricao()).isPresent()) {
				throw new TipoProcessoComNomeJaExistenteException();
			}
		}
	}

	private TipoProcesso buscarTipoProcessoPeloCodigo(Long id) {
		TipoProcesso tipoProcessoSalvo = tipoProcessoRepository.findById(id).
				orElseThrow(() -> new EmptyResultDataAccessException(1));
		return tipoProcessoSalvo;
	}
}
