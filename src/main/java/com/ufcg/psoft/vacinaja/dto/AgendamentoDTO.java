package com.ufcg.psoft.vacinaja.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AgendamentoDTO {

	private String cartaoSUS;
	private LocalDateTime data;
	
	public String getCartaoSUS() {
		return this.cartaoSUS;
	}
	
	public LocalDateTime getData() {
		return this.data;
	}
}
