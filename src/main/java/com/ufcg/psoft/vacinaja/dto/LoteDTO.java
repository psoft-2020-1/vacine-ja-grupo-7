package com.ufcg.psoft.vacinaja.dto;

import java.util.Date;

public class LoteDTO {
	private Date dataDeValidade;
	
	private Long numeroDeDoses;
	
	private String tipoDaVacina;
	
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
