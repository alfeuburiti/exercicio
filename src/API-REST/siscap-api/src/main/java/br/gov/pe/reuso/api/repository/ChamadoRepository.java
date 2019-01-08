package br.gov.pe.reuso.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.pe.reuso.api.model.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

}
