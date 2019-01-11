package br.gov.pe.reuso.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.pe.reuso.api.model.ProcessoSolicitacao;

public interface ProcessoSolicitacaoRepository extends JpaRepository<ProcessoSolicitacao, Long> {

}
