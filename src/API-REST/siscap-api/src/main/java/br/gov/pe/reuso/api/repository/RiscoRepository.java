package br.gov.pe.reuso.api.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import br.gov.pe.reuso.api.model.Risco;

public interface RiscoRepository extends JpaRepository<Risco, Long> {

	Optional<Risco> findByCategoria(String categoria);
	
	@Query("SELECT r FROM Risco r WHERE r.categoria = :categoria AND r.id != :idCategoria")
	List<Risco> buscarPorCategoriaComIdDiferenteDoInformado(@Param("categoria") String categoria, @Param("idCategoria") Long idCategoria);

}
