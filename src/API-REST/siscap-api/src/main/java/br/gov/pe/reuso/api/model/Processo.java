package br.gov.pe.reuso.api.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="PROCESSO")
public class Processo extends BaseEntity {
	
	private String descricao;
	private String numero;
	private String ocorrencia;
	private String situacao;
	private String origem;
	private String mes;
	private String ano;
	@DateTimeFormat(pattern="yyyy-mm-dd hh:mm:ss")
	private LocalDateTime dataConclusao;
	private TipoProcesso tipoProcesso;
	private ProcessoStatus processoStatus;
	private ProcessoLocalizacao processoLocalizacao;
	private ArrayList<Lona> lonas;
	private ArrayList<ProcessoSolicitacao> solicitacoes;
	private ArrayList<Vistoria> vistorias;
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getNumero() {
		return numero;
	}
	
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public String getOcorrencia() {
		return ocorrencia;
	}
	
	public void setOcorrencia(String ocorrencia) {
		this.ocorrencia = ocorrencia;
	}
	
	public String getSituacao() {
		return situacao;
	}
	
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	public String getOrigem() {
		return origem;
	}
	
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	
	public String getMes() {
		return mes;
	}
	
	public void setMes(String mes) {
		this.mes = mes;
	}
	
	public String getAno() {
		return ano;
	}
	
	public void setAno(String ano) {
		this.ano = ano;
	}
	
	public LocalDateTime getDataConclusao() {
		return dataConclusao;
	}
	
	public void setDataConclusao(LocalDateTime dataConclusao) {
		this.dataConclusao = dataConclusao;
	}
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="tipoprocesso")
	public TipoProcesso getTipoProcesso() {
		return tipoProcesso;
	}
	
	public void setTipoProcesso(TipoProcesso tipoProcesso) {
		this.tipoProcesso = tipoProcesso;
	}
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="processostatus")
	public ProcessoStatus getProcessoStatus() {
		return processoStatus;
	}
	
	public void setProcessoStatus(ProcessoStatus processoStatus) {
		this.processoStatus = processoStatus;
	}
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="localizacao")
	public ProcessoLocalizacao getProcessoLocalizacao() {
		return processoLocalizacao;
	}
	
	public void setProcessoLocalizacao(ProcessoLocalizacao processoLocalizacao) {
		this.processoLocalizacao = processoLocalizacao;
	}
	
	@OneToMany
	@JoinColumn(name="lona")
	public ArrayList<Lona> getLonas() {
		return lonas;
	}
	
	public void setLonas(ArrayList<Lona> lonas) {
		this.lonas = lonas;
	}
	
	@OneToMany
	@JoinColumn(name="solicitacao")
	public ArrayList<ProcessoSolicitacao> getSolicitacoes() {
		return solicitacoes;
	}
	
	public void setSolicitacoes(ArrayList<ProcessoSolicitacao> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}
	
	@OneToMany
	@JoinColumn(name="vistoria")
	public ArrayList<Vistoria> getVistorias() {
		return vistorias;
	}
	
	public void setVistorias(ArrayList<Vistoria> vistorias) {
		this.vistorias = vistorias;
	}

}
