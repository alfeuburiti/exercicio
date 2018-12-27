package br.gov.pe.reuso.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.gov.pe.reuso.api.model.Regional;
import br.gov.pe.reuso.api.repository.RegionalRepository;
import br.gov.pe.reuso.api.service.exception.RegionalComNomeJaExistenteException;

@Service
public class RegionalService {

	@Autowired
	private RegionalRepository regionalRepository;
	
	public Regional atualizar(Long id, Regional regional) {
		Regional regionalSalvo = buscarRegionalPeloCodigo(id);
		BeanUtils.copyProperties(regional, regionalSalvo, "id", "dataCriacao", "usuarioCriacao");
		validarNomeRegionalDuplicado(regionalSalvo);
		regionalRepository.save(regionalSalvo);
		
		return regionalSalvo;
	}
	
	public Regional adicionar(Regional regional) {

		validarNomeRegionalDuplicado(regional);
		Regional regionalSalvo = regionalRepository.save(regional);
		
		return regionalSalvo;
	}

	private void validarNomeRegionalDuplicado(Regional regional) {
		if (regional.isAlterando()) {
			List<Regional> regionais = regionalRepository
					.buscarPorNomeComIdDiferenteDoInformado(regional.getDescricao(), regional.getId());
			if (!regionais.isEmpty()) {
				throw new RegionalComNomeJaExistenteException();
			}
		} else {
			if(regionalRepository.findByDescricao(regional.getDescricao()).isPresent()) {
				throw new RegionalComNomeJaExistenteException();
			}
		}
	}

	private Regional buscarRegionalPeloCodigo(Long id) {
		Regional regionalSalvo = regionalRepository.findById(id).
				orElseThrow(() -> new EmptyResultDataAccessException(1));
		return regionalSalvo;
	}
}
