package br.gov.pe.reuso.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.pe.reuso.api.model.Publicacao;
import br.gov.pe.reuso.api.repository.publicacao.PublicacaoRepositoryQuery;

public interface PublicacaoRepository extends JpaRepository<Publicacao, Long>, PublicacaoRepositoryQuery {

}
