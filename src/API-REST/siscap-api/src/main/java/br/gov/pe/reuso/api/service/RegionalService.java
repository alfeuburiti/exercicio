package br.gov.pe.reuso.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.gov.pe.reuso.api.model.Regional;
import br.gov.pe.reuso.api.repository.RegionalRepository;
import br.gov.pe.reuso.api.service.exception.RegionalComDescricaoJaExistenteException;

@Service
public class RegionalService {

	@Autowired
	private RegionalRepository regionalRepository;
	
	public Regional atualizar(Long id, Regional regional) {
		Regional regionalSalvo = buscarRegionalPeloCodigo(id);
		BeanUtils.copyProperties(regional, regionalSalvo, "id", "dataCriacao", "usuarioCriacao");
		validarDescricaoRegionalDuplicado(regionalSalvo);
		regionalRepository.save(regionalSalvo);
		
		return regionalSalvo;
	}
	
	public Regional adicionar(Regional regional) {

		validarDescricaoRegionalDuplicado(regional);
		Regional regionalSalvo = regionalRepository.save(regional);
		
		return regionalSalvo;
	}

	private void validarDescricaoRegionalDuplicado(Regional regional) {
		if (regional.isAlterando()) {
			List<Regional> regionais = regionalRepository
					.buscarPorDescricaoComIdDiferenteDoInformado(regional.getDescricao(), regional.getId());
			if (!regionais.isEmpty()) {
				throw new RegionalComDescricaoJaExistenteException();
			}
		} else {
			if(regionalRepository.findByDescricao(regional.getDescricao()).isPresent()) {
				throw new RegionalComDescricaoJaExistenteException();
			}
		}
	}

	private Regional buscarRegionalPeloCodigo(Long id) {
		Regional regionalSalvo = regionalRepository.findById(id).
				orElseThrow(() -> new EmptyResultDataAccessException(1));
		return regionalSalvo;
	}
}
