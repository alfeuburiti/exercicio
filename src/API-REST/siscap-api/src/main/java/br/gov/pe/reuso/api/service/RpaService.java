package br.gov.pe.reuso.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.gov.pe.reuso.api.model.Rpa;
import br.gov.pe.reuso.api.repository.RpaRepository;
import br.gov.pe.reuso.api.service.exception.RpaComNomeJaExistenteException;

@Service
public class RpaService {
	
	@Autowired
	private RpaRepository rpaRepository;
	
	public Rpa atualizar(Long id, Rpa rpa) {
		Rpa rpaSalvo = buscarRpaPeloCodigo(id);
		BeanUtils.copyProperties(rpa, rpaSalvo, "id", "dataCriacao", "usuarioCriacao");
		validarNomeRpaDuplicado(rpaSalvo);
		rpaRepository.save(rpaSalvo);
		
		return rpaSalvo;
	}
	
	public Rpa adicionar(Rpa rpa) {

		validarNomeRpaDuplicado(rpa);
		Rpa rpaSalvo = rpaRepository.save(rpa);
		
		return rpaSalvo;
	}

	private void validarNomeRpaDuplicado(Rpa rpa) {
		if (rpa.isAlterando()) {
			List<Rpa> rpas = rpaRepository
					.buscarPorNomeComIdDiferenteDoInformado(rpa.getDescricao(), rpa.getId());
			if (!rpas.isEmpty()) {
				throw new RpaComNomeJaExistenteException();
			}
		} else {
			if(rpaRepository.findByDescricao(rpa.getDescricao()).isPresent()) {
				throw new RpaComNomeJaExistenteException();
			}
		}
	}

	private Rpa buscarRpaPeloCodigo(Long id) {
		Rpa rpaSalvo = rpaRepository.findById(id).
				orElseThrow(() -> new EmptyResultDataAccessException(1));
		return rpaSalvo;
	}
}
