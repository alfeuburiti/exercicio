package br.gov.pe.reuso.api.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="LONA")
public class Lona extends BaseEntity {

	private boolean situacao;
	@CreationTimestamp
	@DateTimeFormat(pattern="yyyy-mm-dd hh:mm:ss")
	private LocalDateTime data;
	private String justificativa;
	private int metragem;
	private int quantidadePontos;
	
	public boolean isSituacao() {
		return situacao;
	}
	
	public void setSituacao(boolean situacao) {
		this.situacao = situacao;
	}
	
	public LocalDateTime getDataColocacao() {
		return dataColocacao;
	}
	
	public void setDataColocacao(LocalDateTime dataColocacao) {
		this.dataColocacao = dataColocacao;
	}
	
	public String getJustificativa() {
		return justificativa;
	}
	
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}
	
	public int getMetragem() {
		return metragem;
	}
	
	public void setMetragem(int metragem) {
		this.metragem = metragem;
	}
	
	public int getQuantidadePontos() {
		return quantidadePontos;
	}
	
	public void setQuantidadePontos(int quantidadePontos) {
		this.quantidadePontos = quantidadePontos;
	}

}
