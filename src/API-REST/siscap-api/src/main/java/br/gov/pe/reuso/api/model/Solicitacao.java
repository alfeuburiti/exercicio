package br.gov.pe.reuso.api.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name="SOLICITACAO")
public class Solicitacao extends BaseEntity {
	
	@CreationTimestamp
	@DateTimeFormat(pattern="yyyy-mm-dd hh:mm:ss")
	private LocalDateTime momento;	
	private String descricao;
	private String endereco;
	private String roteiro;
	private String origemChamado;
	private boolean vitimas;
	private boolean vitimasFatais;
	private boolean plantao;
	private double longitude;
	private double latitude;
	private Chamado chamado;
	private Regional regional;
	private Localidade localidade;
	private Bairro bairro;
	private Rpa RPA;
	private MicroRegiao microRegiao;
	
	public LocalDateTime getMomento() {
		return momento;
	}
	
	public void setMomento(LocalDateTime momento) {
		this.momento = momento;
	}
	
	@NotNull
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getEndereco() {
		return endereco;
	}
	
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	@NotNull
	public String getRoteiro() {
		return roteiro;
	}
	
	public void setRoteiro(String roteiro) {
		this.roteiro = roteiro;
	}
	
	public String getOrigemChamado() {
		return origemChamado;
	}
	
	public void setOrigemChamado(String origemChamado) {
		this.origemChamado = origemChamado;
	}
	
	@NotNull
	public boolean isVitimas() {
		return vitimas;
	}
	
	public void setVitimas(boolean vitimas) {
		this.vitimas = vitimas;
	}
	
	@NotNull
	public boolean isVitimasFatais() {
		return vitimasFatais;
	}
	
	public void setVitimasFatais(boolean vitimasFatais) {
		this.vitimasFatais = vitimasFatais;
	}
	
	@NotNull
	public boolean isPlantao() {
		return plantao;
	}
	
	public void setPlantao(boolean plantao) {
		this.plantao = plantao;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="regional")
	public Regional getRegional() {
		return regional;
	}
	
	public void setRegional(Regional regional) {
		this.regional = regional;
	}
	
	@ManyToOne
	@JoinColumn(name="localidade")
	public Localidade getLocalidade() {
		return localidade;
	}
	
	public void setLocalidade(Localidade localidade) {
		this.localidade = localidade;
	}
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="bairro")
	public Bairro getBairro() {
		return bairro;
	}
	
	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}
	
	@ManyToOne
	@JoinColumn(name="rpa")
	public Rpa getRPA() {
		return RPA;
	}
	public void setRPA(Rpa rPA) {
		RPA = rPA;
	}
	
	@ManyToOne
	@JoinColumn(name="microregiao")
	public MicroRegiao getMicroRegiao() {
		return microRegiao;
	}
	
	public void setMicroRegiao(MicroRegiao microRegiao) {
		this.microRegiao = microRegiao;
	}

	@ManyToOne
	@JoinColumn(name="chamado")
	public Chamado getChamado() {
		return chamado;
	}

	public void setChamado(Chamado chamado) {
		this.chamado = chamado;
	}
	
}


