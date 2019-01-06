package br.gov.pe.reuso.api.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.gov.pe.reuso.api.model.Avaliador;

public interface AvaliadorRepository extends JpaRepository<Avaliador, Long> {

	Optional<Avaliador> findByEspecialidade(String especialidade);
	
	@Query("SELECT a FROM Avaliador a WHERE a.especialidade = :especialidade AND a.id != :idEspecialidade")
	List<Avaliador> buscarPorEspecialidadeComIdDiferenteDoInformado(@Param("especialidade") String especialidade, @Param("idEspecialidade") Long idEspecialidade);

}
