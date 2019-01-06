package br.gov.pe.reuso.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.gov.pe.reuso.api.model.ProcessoLocalizacao;

public interface ProcessoLocalizacaoRepository extends JpaRepository<ProcessoLocalizacao, Long> {

	Optional<ProcessoLocalizacao> findByDescricao(String descricao);
	
	@Query("SELECT pl FROM ProcessoLocalizacao pl WHERE pl.descricao = :descricao AND pl.id != :idDescricao")
	List<ProcessoLocalizacao> buscarPorDescricaoComIdDiferenteDoInformado(@Param("descricao") String descricao, @Param("idDescricao") Long idDescricao);


}
