package br.gov.pe.reuso.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.gov.pe.reuso.api.model.MicroRegiao;
import br.gov.pe.reuso.api.repository.MicroRegiaoRepository;
import br.gov.pe.reuso.api.service.exception.MicroRegiaoComDescricaoJaExistenteException;


@Service
public class MicroRegiaoService {

	@Autowired
	private MicroRegiaoRepository microRegiaoRepository;
	
	public MicroRegiao atualizar(Long id, MicroRegiao microRegiao) {
		MicroRegiao microRegiaoSalvo = buscarMicroRegiaoPeloCodigo(id);
		BeanUtils.copyProperties(microRegiao, microRegiaoSalvo, "id", "dataCriacao", "usuarioCriacao");
		validarDescricaoMicroRegiaoDuplicado(microRegiaoSalvo);
		microRegiaoRepository.save(microRegiaoSalvo);
		
		return microRegiaoSalvo;
	}

	public MicroRegiao adicionar(MicroRegiao microRegiao) {

		validarDescricaoMicroRegiaoDuplicado(microRegiao);
		MicroRegiao microRegiaoSalvo = microRegiaoRepository.save(microRegiao);
		
		return microRegiaoSalvo;
	}

	private void validarDescricaoMicroRegiaoDuplicado(MicroRegiao microRegiao) {
		if (microRegiao.isAlterando()) {
			List<MicroRegiao> microRegioes = microRegiaoRepository
					.buscarPorDescricaoComIdDiferenteDoInformado(microRegiao.getDescricao(), microRegiao.getId());
			if (!microRegioes.isEmpty()) {
				throw new MicroRegiaoComDescricaoJaExistenteException();
			}
		} else {
			if(microRegiaoRepository.findByDescricao(microRegiao.getDescricao()).isPresent()) {
				throw new MicroRegiaoComDescricaoJaExistenteException();
			}
		}
	}

	private MicroRegiao buscarMicroRegiaoPeloCodigo(Long id) {
		MicroRegiao microRegiaoSalvo = microRegiaoRepository.findById(id).
				orElseThrow(() -> new EmptyResultDataAccessException(1));
		return microRegiaoSalvo;
	}
}
