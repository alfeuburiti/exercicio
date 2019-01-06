package br.gov.pe.reuso.api.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="PROCESSO_STATUS")
public class ProcessoStatus extends BaseEntity {

	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
