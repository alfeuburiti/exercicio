package br.gov.pe.reuso.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.gov.pe.reuso.api.model.Localidade;

public interface LocalidadeRepository extends JpaRepository<Localidade, Long> {

	Optional<Localidade> findByDescricao(String descricao);
	
	@Query("SELECT l FROM Localidade l WHERE l.descricao = :descricao AND l.id != :idDescricao")
	List<Localidade> buscarPorDescricaoComIdDiferenteDoInformado(@Param("descricao") String descricao, @Param("idDescricao") Long idDescricao);


}
