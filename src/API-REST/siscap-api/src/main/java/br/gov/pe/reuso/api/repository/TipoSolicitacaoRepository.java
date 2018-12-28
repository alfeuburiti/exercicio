package br.gov.pe.reuso.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.gov.pe.reuso.api.model.TipoSolicitacao;


public interface TipoSolicitacaoRepository extends JpaRepository<TipoSolicitacao, Long> {

	Optional<TipoSolicitacao> findByDescricao(String descricao);
	
	@Query("SELECT ts FROM TipoSolicitacao ts WHERE ts.descricao = :descricao AND ts.id != :idDescricao")
	List<TipoSolicitacao> buscarPorDescricaoComIdDiferenteDoInformado(@Param("descricao") String descricao, @Param("idDescricao") Long idDescricao);

}
