package br.gov.pe.reuso.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.gov.pe.reuso.api.model.TipoProcesso;


public interface TipoProcessoRepository extends JpaRepository<TipoProcesso, Long> {

	Optional<TipoProcesso> findByDescricao(String descricao);
	
	@Query("SELECT tp FROM TipoProcesso tp WHERE tp.descricao = :descricao AND tp.id != :idDescricao")
	List<TipoProcesso> buscarPorDescricaoComIdDiferenteDoInformado(@Param("descricao") String descricao, @Param("idDescricao") Long idDescricao);

}
