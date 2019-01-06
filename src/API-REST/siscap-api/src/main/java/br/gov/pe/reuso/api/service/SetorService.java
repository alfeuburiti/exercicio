package br.gov.pe.reuso.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.gov.pe.reuso.api.model.Setor;
import br.gov.pe.reuso.api.repository.SetorRepository;
import br.gov.pe.reuso.api.service.exception.SetorComAreaJaExistenteException;

@Service
public class SetorService {

	@Autowired
	private SetorRepository setorRepository;
	
	public Setor atualizar(Long id, Setor setor) {
		Setor setorSalvo = buscarSetorPeloCodigo(id);
		BeanUtils.copyProperties(setor, setorSalvo, "id", "dataCriacao", "usuarioCriacao");
		validarAreaSetorDuplicado(setorSalvo);
		setorRepository.save(setorSalvo);
		
		return setorSalvo;
	}
	
	public Setor adicionar(Setor setor) {

		validarAreaSetorDuplicado(setor);
		Setor setorSalvo = setorRepository.save(setor);
		
		return setorSalvo;
	}

	private void validarAreaSetorDuplicado(Setor setor) {
		if (setor.isAlterando()) {
			List<Setor> setors = setorRepository
					.buscarPorAreaComIdDiferenteDoInformado(setor.getArea(), setor.getId());
			if (!setors.isEmpty()) {
				throw new SetorComAreaJaExistenteException();
			}
		} else {
			if(setorRepository.findByArea(setor.getArea()).isPresent()) {
				throw new SetorComAreaJaExistenteException();
			}
		}
	}

	private Setor buscarSetorPeloCodigo(Long id) {
		Setor setorSalvo = setorRepository.findById(id).
				orElseThrow(() -> new EmptyResultDataAccessException(1));
		return setorSalvo;
	}
}
