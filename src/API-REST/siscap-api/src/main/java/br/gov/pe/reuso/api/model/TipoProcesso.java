package br.gov.pe.reuso.api.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="TIPO_PROCESSO")
public class TipoProcesso extends BaseEntity {

	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}	

}
