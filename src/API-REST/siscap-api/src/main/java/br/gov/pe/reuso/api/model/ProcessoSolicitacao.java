package br.gov.pe.reuso.api.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="PROCESSO_SOLICITACAO")
public class ProcessoSolicitacao {
	
	private Processo processo;
	private Solicitacao solicitacao;
	private String atividade;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="processo")
	public Processo getProcesso() {
		return processo;
	}
	
	public void setProcesso(Processo processo) {
		this.processo = processo;
	}
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="solicitacao")
	public Solicitacao getSolicitacao() {
		return solicitacao;
	}
	
	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}
	
	public String getAtividade() {
		return atividade;
	}
	
	public void setAtividade(String atividade) {
		this.atividade = atividade;
	}

}
