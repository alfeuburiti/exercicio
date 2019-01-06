package br.gov.pe.reuso.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.gov.pe.reuso.api.model.Avaliador;
import br.gov.pe.reuso.api.repository.AvaliadorRepository;
import br.gov.pe.reuso.api.service.exception.AvaliadorComEspecialidadeJaExistenteException;

@Service
public class AvaliadorService {

	@Autowired
	private AvaliadorRepository avaliadorRepository;
	
	public Avaliador atualizar(Long id, Avaliador avaliador) {
		Avaliador avaliadorSalvo = buscarAvaliadorPeloCodigo(id);
		BeanUtils.copyProperties(avaliador, avaliadorSalvo, "id", "dataCriacao", "usuarioCriacao");
		validarEspecialidadeAvaliadorDuplicado(avaliadorSalvo);
		avaliadorRepository.save(avaliadorSalvo);
		
		return avaliadorSalvo;
	}
	
	public Avaliador adicionar(Avaliador avaliador) {

		validarEspecialidadeAvaliadorDuplicado(avaliador);
		Avaliador avaliadorSalvo = avaliadorRepository.save(avaliador);
		
		return avaliadorSalvo;
	}

	private void validarEspecialidadeAvaliadorDuplicado(Avaliador avaliador) {
		if (avaliador.isAlterando()) {
			List<Avaliador> avaliadors = avaliadorRepository
					.buscarPorEspecialidadeComIdDiferenteDoInformado(avaliador.getEspecialidade(), avaliador.getId());
			if (!avaliadors.isEmpty()) {
				throw new AvaliadorComEspecialidadeJaExistenteException();
			}
		} else {
			if(avaliadorRepository.findByEspecialidade(avaliador.getEspecialidade()).isPresent()) {
				throw new AvaliadorComEspecialidadeJaExistenteException();
			}
		}
	}

	private Avaliador buscarAvaliadorPeloCodigo(Long id) {
		Avaliador avaliadorSalvo = avaliadorRepository.findById(id).
				orElseThrow(() -> new EmptyResultDataAccessException(1));
		return avaliadorSalvo;
	}
	
}
