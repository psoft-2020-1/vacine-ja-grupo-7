package com.ufcg.psoft.vacinaja.utils;

import com.ufcg.psoft.vacinaja.enums.ComorbidadeEnum;
import com.ufcg.psoft.vacinaja.model.Comorbidade;

public class ComorbidadeEnumToComorbidade {
	
	public static Comorbidade toComorbidade(ComorbidadeEnum comorbidadeEnum) {
		Comorbidade comorbidade;
		
		Long idComorbidade = comorbidadeEnum.getValue();
		
		if (idComorbidade == 1L) {
			comorbidade = new Comorbidade("DIABETES");
			
		} else if (idComorbidade == 2L) {
			comorbidade = new Comorbidade("PRESSAO_ALTA");
			
		} else {
			comorbidade = new Comorbidade("ASMA");
		}
		
		return comorbidade;
	}

}
