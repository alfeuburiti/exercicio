package br.gov.pe.reuso.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.gov.pe.reuso.api.model.Bairro;


public interface BairroRepository extends JpaRepository<Bairro, Long> {

	Optional<Bairro> findByDescricao(String descricao);
	
	@Query("SELECT ba FROM Bairro ba WHERE ba.descricao = :descricao AND ba.id != :idDescricao")
	List<Bairro> buscarPorNomeComIdDiferenteDoInformado(@Param("descricao") String descricao, @Param("idDescricao") Long idDescricao);

}
