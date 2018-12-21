package br.gov.pe.reuso.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.pe.reuso.api.model.Arquivo;

public interface ArquivoRepository extends JpaRepository<Arquivo, Long> {
	
}
