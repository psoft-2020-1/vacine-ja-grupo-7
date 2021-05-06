package com.ufcg.psoft.vacinaja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.vacinaja.dto.LoteDTO;
import com.ufcg.psoft.vacinaja.model.Lote;
import com.ufcg.psoft.vacinaja.repository.LoteRepository;

@Service
public class LoteServiceImpl implements LoteService {

	@Autowired
	private LoteRepository loteRepository;
	
	@Override
	public Lote cadastrarLote(LoteDTO loteDTO) {
		Lote novoLote = new Lote(loteDTO);
		
		loteRepository.save(novoLote);
		
		return novoLote;
	}
}
