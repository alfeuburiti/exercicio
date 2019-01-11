package br.gov.pe.reuso.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.gov.pe.reuso.api.model.Lona;
import br.gov.pe.reuso.api.repository.LonaRepository;

@Service
public class LonaService {

	@Autowired
	private LonaRepository lonaRepository;
	
	public Lona atualizar(Long id, Lona lona) {
		Lona lonaSalvo = buscarLonaPeloCodigo(id);
		BeanUtils.copyProperties(lona, lonaSalvo, "id", "dataCriacao", "usuarioCriacao");
		lonaRepository.save(lonaSalvo);
		
		return lonaSalvo;
	}
	
	public Lona adicionar(Lona lona) {

		Lona lonaSalvo = lonaRepository.save(lona);
		return lonaSalvo;
	}

	private Lona buscarLonaPeloCodigo(Long id) {
		Lona lonaSalvo = lonaRepository.findById(id).
				orElseThrow(() -> new EmptyResultDataAccessException(1));
		return lonaSalvo;
	}
}
