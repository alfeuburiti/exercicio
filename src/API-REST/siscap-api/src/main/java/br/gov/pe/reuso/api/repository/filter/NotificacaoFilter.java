package br.gov.pe.reuso.api.repository.filter;

import br.gov.pe.reuso.api.model.enums.NotificacaoTipo;

public class NotificacaoFilter {

	private NotificacaoTipo tipo;
	private Long idPublicacao;
	
	public NotificacaoTipo getTipo() {
		return tipo;
	}
	
	public void setTipo(NotificacaoTipo tipo) {
		this.tipo = tipo;
	}

	public Long getIdPublicacao() {
		return idPublicacao;
	}

	public void setIdPublicacao(Long idPublicacao) {
		this.idPublicacao = idPublicacao;
	}
	
}
