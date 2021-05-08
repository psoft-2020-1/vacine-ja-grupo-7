package com.ufcg.psoft.vacinaja.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.ufcg.psoft.vacinaja.dto.LoteDTO;

@Entity
public class Lote {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
	
	private Date dataDeValidade;
	
	private Long numeroDeDoses;
	
	@ManyToOne
	private Vacina vacina;

	public Lote() {}
	
	public Lote(Date dataDeValidade, Long numeroDeDoses, Vacina vacina) {
		this.dataDeValidade = dataDeValidade;
		this.numeroDeDoses = numeroDeDoses;
		this.vacina = vacina;
	}

	public Long getId() {
		return id;
	}

	public Date getDataDeValidade() {
		return dataDeValidade;
	}

	public void setDataDeValidade(Date dataDeValidade) {
		this.dataDeValidade = dataDeValidade;
	}

	public Long getNumeroDeDoses() {
		return numeroDeDoses;
	}

	public void setNumeroDeDoses(Long numeroDeDoses) {
		this.numeroDeDoses = numeroDeDoses;
	}

	public Vacina getVacina() {
		return vacina;
	}

	public void setVacina(Vacina vacina) {
		this.vacina = vacina;
	}
}
