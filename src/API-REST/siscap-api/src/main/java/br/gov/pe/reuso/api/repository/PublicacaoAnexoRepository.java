package br.gov.pe.reuso.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.gov.pe.reuso.api.model.PublicacaoAnexo;

public interface PublicacaoAnexoRepository extends JpaRepository<PublicacaoAnexo, Long> {
	
	@Query("SELECT new br.gov.pe.reuso.api.model.PublicacaoAnexo(pa.id, pa.dataCriacao, pa.usuarioCriacao, pa.dataAtualizacao, pa.usuarioAtualizacao,pa.nome, pa.sucesso, pa.publicacao.id, pa.arquivo.id)  FROM PublicacaoAnexo pa WHERE pa.publicacao.id = :idPublicacao")
	List<PublicacaoAnexo> buscarPorIdPublicacao(@Param("idPublicacao") Long idPublicacao);
	
	
	

}



