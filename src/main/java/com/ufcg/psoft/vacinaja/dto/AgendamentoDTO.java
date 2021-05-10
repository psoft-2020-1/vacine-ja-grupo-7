package com.ufcg.psoft.vacinaja.dto;

import java.time.LocalDate;

public class AgendamentoDTO {

	private String cartaoSUS;
	private LocalDate data;
	
	public String getCartaoSUS() {
		return this.cartaoSUS;
	}
	
	public LocalDate getData() {
		return this.data;
	}
}
