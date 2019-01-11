package br.gov.pe.reuso.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.gov.pe.reuso.api.model.ProcessoStatus;
import br.gov.pe.reuso.api.repository.ProcessoStatusRepository;
import br.gov.pe.reuso.api.service.exception.ProcessoStatusComDescricaoJaExistenteException;

@Service
public class ProcessoStatusService {

	@Autowired
	private ProcessoStatusRepository processoStatusRepository;
	
	public ProcessoStatus atualizar(Long id, ProcessoStatus processoStatus) {
		ProcessoStatus processoStatusSalvo = buscarProcessoStatusPeloCodigo(id);
		BeanUtils.copyProperties(processoStatus, processoStatusSalvo, "id", "dataCriacao", "usuarioCriacao");
		validarDescricaoProcessoStatusDuplicado(processoStatusSalvo);
		processoStatusRepository.save(processoStatusSalvo);
		
		return processoStatusSalvo;
	}
	
	public ProcessoStatus adicionar(ProcessoStatus processoStatus) {

		validarDescricaoProcessoStatusDuplicado(processoStatus);
		ProcessoStatus processoStatusSalvo = processoStatusRepository.save(processoStatus);
		
		return processoStatusSalvo;
	}

	private void validarDescricaoProcessoStatusDuplicado(ProcessoStatus processoStatus) {
		if (processoStatus.isAlterando()) {
			List<ProcessoStatus> processoStatuss = processoStatusRepository
					.buscarPorDescricaoComIdDiferenteDoInformado(processoStatus.getDescricao(), processoStatus.getId());
			if (!processoStatuss.isEmpty()) {
				throw new ProcessoStatusComDescricaoJaExistenteException();
			}
		} else {
			if(processoStatusRepository.findByDescricao(processoStatus.getDescricao()).isPresent()) {
				throw new ProcessoStatusComDescricaoJaExistenteException();
			}
		}
	}

	private ProcessoStatus buscarProcessoStatusPeloCodigo(Long id) {
		ProcessoStatus processoStatusSalvo = processoStatusRepository.findById(id).
				orElseThrow(() -> new EmptyResultDataAccessException(1));
		return processoStatusSalvo;
	}
}
