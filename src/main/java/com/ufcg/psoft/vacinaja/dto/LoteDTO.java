package com.ufcg.psoft.vacinaja.dto;

import java.util.Date;

public class LoteDTO {
	private Long idVacina;
	
	private Date dataDeValidade;
	
	private Long numeroDeDoses;
	
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

	public Long getIdVacina() {
		return idVacina;
	}

	public void setIdVacina(Long idVacina) {
		this.idVacina = idVacina;
	}
}
