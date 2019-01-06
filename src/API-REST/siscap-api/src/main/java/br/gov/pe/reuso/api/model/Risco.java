package br.gov.pe.reuso.api.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="RISCO")
public class Risco extends BaseEntity {

	private String categoria;

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

}
