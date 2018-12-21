package br.gov.pe.reuso.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.pe.reuso.api.model.NotificacaoConfig;
import br.gov.pe.reuso.api.repository.notificacao.config.NotificacaoConfigRepositoryQuery;

public interface NotificacaoConfigRepository extends JpaRepository<NotificacaoConfig, Long>, NotificacaoConfigRepositoryQuery {

}
