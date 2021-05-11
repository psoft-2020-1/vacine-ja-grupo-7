package com.ufcg.psoft.vacinaja.comparators;

import java.util.Comparator;

import com.ufcg.psoft.vacinaja.model.Lote;

/**
 * Compara dois lotes com base nas datas de validade.
 */
public class LoteValidadeComparator implements Comparator<Lote> {

	@Override
	public int compare(Lote lote1, Lote lote2) {
		return lote1.getDataDeValidade().compareTo(lote2.getDataDeValidade());
	}

}
