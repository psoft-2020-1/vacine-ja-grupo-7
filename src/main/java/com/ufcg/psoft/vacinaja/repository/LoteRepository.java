package com.ufcg.psoft.vacinaja.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.psoft.vacinaja.comparators.LoteValidadeComparator;
import com.ufcg.psoft.vacinaja.model.Lote;
import com.ufcg.psoft.vacinaja.model.Vacina;
import com.ufcg.psoft.vacinaja.states.HabilitadoPrimeiraDoseState;
import com.ufcg.psoft.vacinaja.states.HabilitadoSegundaDoseState;
import com.ufcg.psoft.vacinaja.states.VacinacaoState;

public interface LoteRepository extends JpaRepository<Lote, Long> {
	
	default Optional<Lote> proximoLoteByState(VacinacaoState estadoVacinacao, Vacina vacina) {
		Optional<Lote> optionalLote = Optional.empty();
		
		if (estadoVacinacao instanceof HabilitadoPrimeiraDoseState) {
			optionalLote = proximoLotePrimeiraDose(vacina);
		
		} else if (estadoVacinacao instanceof HabilitadoSegundaDoseState) {
			optionalLote = proximoLoteSegundaDose(vacina);
		}
		
		return optionalLote;
	}
	
	/**
	 * Busca, entre os lotes associados a uma vacina específica, 
	 * aquele que contém unidades reservadas para a primeira aplicação e está mais próximo do vencimento.
	 * 
	 * @param vacina A vacina usada como parâmetro de busca.
	 * @return Um Optional, que fica vazio quando nenhum lote válido é encontrado.
	 */
	default Optional<Lote> proximoLotePrimeiraDose(Vacina vacina) {
		Optional<Lote> optionalLote = Optional.empty();
		
		List<Lote> lotes = findByVacina(vacina);
		
		LoteValidadeComparator comparador = new LoteValidadeComparator();
		lotes.sort(comparador);
		
		for (Lote lote : lotes) {
			if (lote.getReservadasPrimeiraDose() > 0) {
				optionalLote = Optional.of(lote);
				break;
			}
		}
		
		return optionalLote;
	}
	
	/**
	 * Busca, entre os lotes associados a uma vacina específica, 
	 * aquele que contém unidades reservadas para a segunda aplicação e está mais próximo do vencimento.
	 * 
	 * @param vacina A vacina usada como parâmetro de busca.
	 * @return Um Optional, que fica vazio quando nenhum lote válido é encontrado.
	 */
	default Optional<Lote> proximoLoteSegundaDose(Vacina vacina) {
		Optional<Lote> optionalLote = Optional.empty();
		
		List<Lote> lotes = findByVacina(vacina);
		
		LoteValidadeComparator comparador = new LoteValidadeComparator();
		lotes.sort(comparador);
		
		for (Lote lote : lotes) {
			if (lote.getReservadasSegundaDose() > 0) {
				optionalLote = Optional.of(lote);
				break;
			}
		}
		
		return optionalLote;
	}
	
	public List<Lote> findByVacina(Vacina vacina);
	
}
