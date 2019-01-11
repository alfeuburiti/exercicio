package br.gov.pe.reuso.api.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="SETOR")
public class Setor extends BaseEntity {

	private String area;

	@NotNull
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
}
