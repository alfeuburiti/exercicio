package br.gov.pe.reuso.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.gov.pe.reuso.api.model.Risco;
import br.gov.pe.reuso.api.repository.RiscoRepository;
import br.gov.pe.reuso.api.service.exception.RiscoComCategoriaJaExistenteException;

@Service
public class RiscoService {

	@Autowired
	private RiscoRepository riscoRepository;
	
	public Risco atualizar(Long id, Risco risco) {
		Risco riscoSalvo = buscarRiscoPeloCodigo(id);
		BeanUtils.copyProperties(risco, riscoSalvo, "id", "dataCriacao", "usuarioCriacao");
		validarCategoriaRiscoDuplicado(riscoSalvo);
		riscoRepository.save(riscoSalvo);
		
		return riscoSalvo;
	}
	
	public Risco adicionar(Risco risco) {

		validarCategoriaRiscoDuplicado(risco);
		Risco riscoSalvo = riscoRepository.save(risco);
		
		return riscoSalvo;
	}

	private void validarCategoriaRiscoDuplicado(Risco risco) {
		if (risco.isAlterando()) {
			List<Risco> riscos = riscoRepository
					.buscarPorCategoriaComIdDiferenteDoInformado(risco.getCategoria(), risco.getId());
			if (!riscos.isEmpty()) {
				throw new RiscoComCategoriaJaExistenteException();
			}
		} else {
			if(riscoRepository.findByCategoria(risco.getCategoria()).isPresent()) {
				throw new RiscoComCategoriaJaExistenteException();
			}
		}
	}

	private Risco buscarRiscoPeloCodigo(Long id) {
		Risco riscoSalvo = riscoRepository.findById(id).
				orElseThrow(() -> new EmptyResultDataAccessException(1));
		return riscoSalvo;
	}
}
