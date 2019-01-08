package br.gov.pe.reuso.api.model;

import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class Chamado extends BaseEntity {

	private List<Solicitacao> solicitacoes;

	@ManyToOne
	@JoinColumn(name="solicitacao")
	public List<Solicitacao> getSolicitacoes() {
		return solicitacoes;
	}

	public void setSolicitacoes(List<Solicitacao> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}
	
}
