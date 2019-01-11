package br.gov.pe.reuso.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.gov.pe.reuso.api.model.Chamado;
import br.gov.pe.reuso.api.repository.ChamadoRepository;

@Service
public class ChamadoService {

	@Autowired
	private ChamadoRepository chamadoRepository;
	
	public Chamado atualizar(Long id, Chamado chamado) {
		Chamado chamadoSalvo = buscarChamadoPeloCodigo(id);
		BeanUtils.copyProperties(chamado, chamadoSalvo, "id", "dataCriacao", "usuarioCriacao");
		chamadoRepository.save(chamadoSalvo);
		
		return chamadoSalvo;
	}
	
	public Chamado adicionar(Chamado chamado) {

		Chamado chamadoSalvo = chamadoRepository.save(chamado);
		return chamadoSalvo;
	}

	private Chamado buscarChamadoPeloCodigo(Long id) {
		Chamado chamadoSalvo = chamadoRepository.findById(id).
				orElseThrow(() -> new EmptyResultDataAccessException(1));
		return chamadoSalvo;
	}
}
