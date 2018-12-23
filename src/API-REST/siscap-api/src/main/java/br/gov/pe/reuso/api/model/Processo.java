package br.gov.pe.reuso.api.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="PROCESSO")
public class Processo extends BaseEntity {
	
	private Long id;
	private String numero;
	//private TipoProcesso tipoProcesso;
	private Long idOrigem;


	@NotNull
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	//public TipoProcesso getTipoProcesso() {
	//	return tipoProcesso;
	//}
	//@NotNull
	//@ManyToOne
	//@JoinColumn(name="id_tipo_processo")
	//public void setTipoProcesso(TipoProcesso tipoProcesso) {
	//	this.tipoProcesso = tipoProcesso;
	//}

	public Long getIdOrigem() {
		return idOrigem;
	}

	public void setIdOrigem(Long idOrigem) {
		this.idOrigem = idOrigem;
	}

	
	

}
