package br.gov.pe.reuso.api.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name="SOLICITACAO")
public class Solicitacao extends BaseEntity {
	
	private int ano;
	private int mes;
	@DateTimeFormat(pattern="yyyy-mm-dd hh:mm:ss")
	private LocalDateTime momento;	
	private String descricao;
	private Regional regional;
	private String localidade;
	private Bairro bairro;
	private String endereco;
	private String roteiro;
	private Rpa RPA;
	private MicroRegiao microRegiao;
	private TipoSolicitacao tipoSolicitacao;
	private Processo processo;
	

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public void setMomento(LocalDateTime momento) {
		this.momento = momento;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public void setRegional(Regional regional) {
		this.regional = regional;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}
	
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public void setRPA(Rpa rPA) {
		RPA = rPA;
	}
	
	public void setMicroRegiao(MicroRegiao microRegiao) {
		this.microRegiao = microRegiao;
	}
	
	public void setRoteiro(String roteiro) {
		this.roteiro = roteiro;
	}
	
	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}
	
	public void setTipoSolicitacao(TipoSolicitacao tipoSolicitacao) {
		this.tipoSolicitacao = tipoSolicitacao;
	}
	
	public void setProcesso(Processo processo) {
		this.processo = processo;
	}
	
	public LocalDateTime getMomento() {
		return momento;
	}

	public String getDescricao() {
		return descricao;
	}

	@NotNull
	@ManyToOne
	@JoinColumn(name="regional")
	public Regional getRegional() {
		return regional;
	}

	public String getLocalidade() {
		return localidade;
	}

	@NotNull
	@ManyToOne
	@JoinColumn(name="bairro")
	public Bairro getBairro() {
		return bairro;
	}

	public String getEndereco() {
		return endereco;
	}

	public String getRoteiro() {
		return roteiro;
	}

	@ManyToOne
	@JoinColumn(name="rpa")
	public Rpa getRPA() {
		return RPA;
	}

	@NotNull
	@ManyToOne
	@JoinColumn(name="microregiao")
	public MicroRegiao getMicroRegiao() {
		return microRegiao;
	}

	@NotNull
	@ManyToOne
	@JoinColumn(name="tiposolicitacao")
	public TipoSolicitacao getTipoSolicitacao() {
		return tipoSolicitacao;
	}
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="processo")
	public Processo getProcesso() {
		return processo;
	}
	
}


