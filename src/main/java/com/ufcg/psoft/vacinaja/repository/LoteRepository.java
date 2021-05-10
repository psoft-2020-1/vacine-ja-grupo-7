package com.ufcg.psoft.vacinaja.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.psoft.vacinaja.comparators.LoteValidadeComparator;
import com.ufcg.psoft.vacinaja.model.Lote;
import com.ufcg.psoft.vacinaja.model.Vacina;

public interface LoteRepository extends JpaRepository<Lote, Long> {
	
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
