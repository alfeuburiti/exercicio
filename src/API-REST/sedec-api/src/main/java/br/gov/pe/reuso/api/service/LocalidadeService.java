package br.gov.pe.reuso.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.gov.pe.reuso.api.model.Localidade;
import br.gov.pe.reuso.api.repository.LocalidadeRepository;
import br.gov.pe.reuso.api.service.exception.LocalidadeComDescricaoJaExistenteException;

@Service
public class LocalidadeService {

	@Autowired
	private LocalidadeRepository localidadeRepository;
	
	public Localidade atualizar(Long id, Localidade localidade) {
		Localidade localidadeSalvo = buscarLocalidadePeloCodigo(id);
		BeanUtils.copyProperties(localidade, localidadeSalvo, "id", "dataCriacao", "usuarioCriacao");
		validarDescricaoLocalidadeDuplicado(localidadeSalvo);
		localidadeRepository.save(localidadeSalvo);
		
		return localidadeSalvo;
	}
	
	public Localidade adicionar(Localidade localidade) {

		validarDescricaoLocalidadeDuplicado(localidade);
		Localidade localidadeSalvo = localidadeRepository.save(localidade);
		
		return localidadeSalvo;
	}

	private void validarDescricaoLocalidadeDuplicado(Localidade localidade) {
		if (localidade.isAlterando()) {
			List<Localidade> localidades = localidadeRepository
					.buscarPorDescricaoComIdDiferenteDoInformado(localidade.getDescricao(), localidade.getId());
			if (!localidades.isEmpty()) {
				throw new LocalidadeComDescricaoJaExistenteException();
			}
		} else {
			if(localidadeRepository.findByDescricao(localidade.getDescricao()).isPresent()) {
				throw new LocalidadeComDescricaoJaExistenteException();
			}
		}
	}

	private Localidade buscarLocalidadePeloCodigo(Long id) {
		Localidade localidadeSalvo = localidadeRepository.findById(id).
				orElseThrow(() -> new EmptyResultDataAccessException(1));
		return localidadeSalvo;
	}
}
