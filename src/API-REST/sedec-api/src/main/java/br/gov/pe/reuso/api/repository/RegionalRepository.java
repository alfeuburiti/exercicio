package br.gov.pe.reuso.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.gov.pe.reuso.api.model.Regional;


public interface RegionalRepository extends JpaRepository<Regional, Long> {

	Optional<Regional> findByDescricao(String descricao);
	
	@Query("SELECT r FROM Regional r WHERE r.descricao = :descricao AND r.id != :idDescricao")
	List<Regional> buscarPorDescricaoComIdDiferenteDoInformado(@Param("descricao") String descricao, @Param("idDescricao") Long idDescricao);

}
