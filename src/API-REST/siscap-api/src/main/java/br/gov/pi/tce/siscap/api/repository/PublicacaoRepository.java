package br.gov.pi.tce.siscap.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.pi.tce.siscap.api.model.Publicacao;

public interface PublicacaoRepository extends JpaRepository<Publicacao, Long> {

}
