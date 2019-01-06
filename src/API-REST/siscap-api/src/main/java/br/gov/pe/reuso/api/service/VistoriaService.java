package br.gov.pe.reuso.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.gov.pe.reuso.api.model.Vistoria;
import br.gov.pe.reuso.api.repository.VistoriaRepository;

@Service
public class VistoriaService {

	@Autowired
	private VistoriaRepository vistoriaRepository;
	
	public Vistoria atualizar(Long id, Vistoria vistoria) {
		Vistoria vistoriaSalvo = buscarVistoriaPeloCodigo(id);
		BeanUtils.copyProperties(vistoria, vistoriaSalvo, "id", "dataCriacao", "usuarioCriacao");
		vistoriaRepository.save(vistoriaSalvo);
		
		return vistoriaSalvo;
	}
	
	public Vistoria adicionar(Vistoria vistoria) {

		Vistoria vistoriaSalvo = vistoriaRepository.save(vistoria);
		return vistoriaSalvo;
	}

	private Vistoria buscarVistoriaPeloCodigo(Long id) {
		Vistoria vistoriaSalvo = vistoriaRepository.findById(id).
				orElseThrow(() -> new EmptyResultDataAccessException(1));
		return vistoriaSalvo;
	}
}
