package com.ufcg.psoft.vacinaja.service;

import java.util.List;

import com.ufcg.psoft.vacinaja.dto.LoteDTO;
import com.ufcg.psoft.vacinaja.model.Lote;

public interface LoteService {
	
	Lote cadastrarLote(LoteDTO loteDTO);

	public List<Lote> listarLotes();

}
