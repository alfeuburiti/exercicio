package br.gov.pe.reuso.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.gov.pe.reuso.api.model.PublicacaoHistorico;

public interface PublicacaoHistoricoRepository extends JpaRepository<PublicacaoHistorico, Long> {
	
	
	@Query("SELECT new br.gov.pe.reuso.api.model.PublicacaoHistorico(ph.mensagem, ph.sucesso, ph.dataCriacao, ph.usuarioCriacao)  FROM PublicacaoHistorico ph WHERE ph.publicacao.id = :idPublicacao")
	List<PublicacaoHistorico> buscarPeloIdPublicacao(@Param("idPublicacao") Long idPublicacao);
}
