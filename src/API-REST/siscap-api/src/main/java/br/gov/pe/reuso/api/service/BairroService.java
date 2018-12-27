package br.gov.pe.reuso.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.gov.pe.reuso.api.model.Bairro;
import br.gov.pe.reuso.api.repository.BairroRepository;
import br.gov.pe.reuso.api.service.exception.BairroComNomeJaExistenteException;

@Service
public class BairroService {

	@Autowired
	private BairroRepository bairroRepository;
	
	public Bairro atualizar(Long id, Bairro bairro) {
		Bairro bairroSalvo = buscarBairroPeloCodigo(id);
		BeanUtils.copyProperties(bairro, bairroSalvo, "id", "dataCriacao", "usuarioCriacao");
		validarNomeBairroDuplicado(bairroSalvo);
		bairroRepository.save(bairroSalvo);
		
		return bairroSalvo;
	}
	
	public Bairro adicionar(Bairro bairro) {

		validarNomeBairroDuplicado(bairro);
		Bairro bairroSalvo = bairroRepository.save(bairro);
		
		return bairroSalvo;
	}

	private void validarNomeBairroDuplicado(Bairro bairro) {
		if (bairro.isAlterando()) {
			List<Bairro> bairros = bairroRepository
					.buscarPorNomeComIdDiferenteDoInformado(bairro.getDescricao(), bairro.getId());
			if (!bairros.isEmpty()) {
				throw new BairroComNomeJaExistenteException();
			}
		} else {
			if(bairroRepository.findByDescricao(bairro.getDescricao()).isPresent()) {
				throw new BairroComNomeJaExistenteException();
			}
		}
	}

	private Bairro buscarBairroPeloCodigo(Long id) {
		Bairro bairroSalvo = bairroRepository.findById(id).
				orElseThrow(() -> new EmptyResultDataAccessException(1));
		return bairroSalvo;
	}
}
