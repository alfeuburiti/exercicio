package br.gov.pe.reuso.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.gov.pe.reuso.api.model.Rpa;


public interface RpaRepository extends JpaRepository<Rpa, Long> {

	Optional<Rpa> findByDescricao(String descricao);
	
	@Query("SELECT rpa FROM Rpa rpa WHERE rpa.descricao = :descricao AND rpa.id != :idDescricao")
	List<Rpa> buscarPorDescricaoComIdDiferenteDoInformado(@Param("descricao") String descricao, @Param("idDescricao") Long idDescricao);

}
