package br.gov.pe.reuso.api.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="VISTORIA")
public class Vistoria extends BaseEntity {
	
	@DateTimeFormat(pattern="yyyy-mm-dd hh:mm:ss")
	private LocalDateTime data;
	private Avaliador avaliador;
	private Risco risco;
	private Setor setor;
	private Processo processo;
	private Localidade localidade;
	private MicroRegiao microRegiao;
	private Rpa rpa;
	
	public LocalDateTime getData() {
		return data;
	}
	
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="avaliador")
	public Avaliador getAvaliador() {
		return avaliador;
	}
	
	public void setAvaliador(Avaliador avaliador) {
		this.avaliador = avaliador;
	}
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="risco")
	public Risco getRisco() {
		return risco;
	}
	
	public void setRisco(Risco risco) {
		this.risco = risco;
	}

	@ManyToOne
	@JoinColumn(name="setor")
	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

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
	@JoinColumn(name="localidade")
	public Localidade getLocalidade() {
		return localidade;
	}

	public void setLocalidade(Localidade localidade) {
		this.localidade = localidade;
	}
	
	@ManyToOne
	@JoinColumn(name="microregiao")
	public MicroRegiao getMicroRegiao() {
		return microRegiao;
	}

	public void setMicroRegiao(MicroRegiao microRegiao) {
		this.microRegiao = microRegiao;
	}

	@NotNull
	@ManyToOne
	@JoinColumn(name="rpa")
	public Rpa getRpa() {
		return rpa;
	}

	public void setRpa(Rpa rpa) {
		this.rpa = rpa;
	}
	
}
