package br.gov.pe.reuso.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.gov.pe.reuso.api.model.MicroRegiao;


public interface MicroRegiaoRepository extends JpaRepository<MicroRegiao, Long> {

	Optional<MicroRegiao> findByDescricao(String descricao);
	
	@Query("SELECT mr FROM MicroRegiao mr WHERE mr.descricao = :descricao AND mr.id != :idDescricao")
	List<MicroRegiao> buscarPorNomeComIdDiferenteDoInformado(@Param("descricao") String descricao, @Param("idDescricao") Long idDescricao);

}
