package br.gov.pe.reuso.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.pe.reuso.api.model.Notificacao;
import br.gov.pe.reuso.api.repository.notificacao.NotificacaoRepositoryQuery;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long>, NotificacaoRepositoryQuery {

}
