package br.gov.pe.reuso.api.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="AVALIADOR")
public class Avaliador extends BaseEntity {

	private String especialidade;

	public String getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}

}
