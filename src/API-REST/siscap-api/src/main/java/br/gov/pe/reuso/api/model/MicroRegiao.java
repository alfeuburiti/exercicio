package br.gov.pe.reuso.api.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="MICROREGIAO")
public class MicroRegiao extends BaseEntity {

	private String descricao;

	@NotNull
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
	
