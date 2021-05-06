package com.ufcg.psoft.vacinaja.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ufcg.psoft.vacinaja.dto.LoteDTO;

@Entity
public class Lote {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
	
	private Date dataDeValidade;
	
	private Long numeroDeDoses;
	
	private String tipoDaVacina;

	public Lote() {}
	
	public Lote(Date dataDeValidade, Long numeroDeDoses, String tipoDeVacina) {
		this.dataDeValidade = dataDeValidade;
		this.numeroDeDoses = numeroDeDoses;
		this.tipoDaVacina = tipoDeVacina;
	}
	
	public Lote(LoteDTO loteDTO) {
		this.dataDeValidade = loteDTO.getDataDeValidade();
		this.numeroDeDoses = loteDTO.getNumeroDeDoses();
		this.tipoDaVacina = loteDTO.getTipoDaVacina();
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

	public String getTipoDaVacina() {
		return tipoDaVacina;
	}

	public void setTipoDaVacina(String tipoDaVacina) {
		this.tipoDaVacina = tipoDaVacina;
	}
}
