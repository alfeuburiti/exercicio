package br.gov.pe.reuso.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.gov.pe.reuso.api.model.Processo;
import br.gov.pe.reuso.api.repository.ProcessoRepository;
import br.gov.pe.reuso.api.service.exception.ProcessoComNumeroJaExistenteException;

@Service
public class ProcessoService {

	@Autowired
	private ProcessoRepository processoRepository;
	
	public Processo atualizar(Long id, Processo processo) {
		Processo processoSalvo = buscarProcessoPeloCodigo(id);
		BeanUtils.copyProperties(processo, processoSalvo, "id", "dataCriacao", "usuarioCriacao");
		validarNumeroProcessoDuplicado(processoSalvo);
		processoRepository.save(processoSalvo);
		
		return processoSalvo;
	}
	
	public Processo adicionar(Processo processo) {

		validarNumeroProcessoDuplicado(processo);
		Processo processoSalvo = processoRepository.save(processo);
		
		return processoSalvo;
	}

	private void validarNumeroProcessoDuplicado(Processo processo) {
		if (processo.isAlterando()) {
			List<Processo> processos = processoRepository
					.buscarPorNumeroComIdDiferenteDoInformado(processo.getNumero(), processo.getId());
			if (!processos.isEmpty()) {
				throw new ProcessoComNumeroJaExistenteException();
			}
		} else {
			if(processoRepository.findByNumero(processo.getNumero()).isPresent()) {
				throw new ProcessoComNumeroJaExistenteException();
			}
		}
	}

	private Processo buscarProcessoPeloCodigo(Long id) {
		Processo processoSalvo = processoRepository.findById(id).
				orElseThrow(() -> new EmptyResultDataAccessException(1));
		return processoSalvo;
	}
}
