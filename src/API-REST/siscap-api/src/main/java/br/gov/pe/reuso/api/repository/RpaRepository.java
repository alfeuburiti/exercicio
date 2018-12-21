package br.gov.pe.reuso.api.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.gov.pe.reuso.api.model.Feriado;
import br.gov.pe.reuso.api.model.Rpa;
import br.gov.pe.reuso.api.repository.feriado.FeriadoRepositoryQuery;

public interface RpaRepository extends JpaRepository<Rpa, Long> {

	@Query("SELECT f.id FROM Feriado f left join f.fontes ff "
			+ " WHERE (f.todasFontes = true OR ff.id = :idFonte) "
			+ "AND ("
				+ "f.data = :data "
				+ "OR ("
					+ "f.fixo = true "
					+ "AND MONTH(f.data) = MONTH(:data) "
					+ "AND DAY(f.data) = DAY(:data) "
				+ ")"
			+ ")")
	List<Long> buscarPorDataEFonte(@Param("data") LocalDate data, @Param("idFonte") Long idFonte);

}
