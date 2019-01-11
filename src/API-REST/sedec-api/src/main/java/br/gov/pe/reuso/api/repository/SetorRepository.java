package br.gov.pe.reuso.api.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import br.gov.pe.reuso.api.model.Setor;

public interface SetorRepository extends JpaRepository<Setor, Long> {

	Optional<Setor> findByArea(String area);
	
	@Query("SELECT s FROM Setor s WHERE s.area = :area AND s.id != :idArea")
	List<Setor> buscarPorAreaComIdDiferenteDoInformado(@Param("area") String area, @Param("idArea") Long idArea);


}
