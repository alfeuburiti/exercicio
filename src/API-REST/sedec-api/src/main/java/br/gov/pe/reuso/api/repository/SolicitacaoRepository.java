package br.gov.pe.reuso.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.gov.pe.reuso.api.model.Solicitacao;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {

}
