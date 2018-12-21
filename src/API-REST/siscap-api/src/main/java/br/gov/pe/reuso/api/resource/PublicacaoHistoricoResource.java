package br.gov.pe.reuso.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.pe.reuso.api.model.PublicacaoHistorico;
import br.gov.pe.reuso.api.repository.PublicacaoHistoricoRepository;

@RestController
@RequestMapping("/historico_publicacao")
public class PublicacaoHistoricoResource {

	
	@Autowired
	private PublicacaoHistoricoRepository publicacaoHistoricoRepository;
	
	@GetMapping("/{id}")
	public List<PublicacaoHistorico> buscarPeloIdPublicacao(@PathVariable Long id) {
		List<PublicacaoHistorico> lista = publicacaoHistoricoRepository.buscarPeloIdPublicacao(id);
		return lista;
	}
}
