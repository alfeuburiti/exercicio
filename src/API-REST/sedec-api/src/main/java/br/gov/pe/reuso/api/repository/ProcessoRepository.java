package br.gov.pe.reuso.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.gov.pe.reuso.api.model.Processo;


public interface ProcessoRepository extends JpaRepository<Processo, Long> {

	Optional<Processo> findByNumero(String numero);
	
	@Query("SELECT p FROM Processo p WHERE p.numero = :numero AND p.id != :idNumero")
	List<Processo> buscarPorNumeroComIdDiferenteDoInformado(@Param("numero") String numero, @Param("idNumero") Long idNumero);

}