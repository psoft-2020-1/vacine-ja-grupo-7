package com.ufcg.psoft.vacinaja;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ufcg.psoft.vacinaja.model.Lote;
import com.ufcg.psoft.vacinaja.model.Vacina;
import com.ufcg.psoft.vacinaja.repository.LoteRepository;
import com.ufcg.psoft.vacinaja.repository.VacinaRepository;

@SpringBootTest
public class LoteRepositoryTests {

	@Autowired
	LoteRepository loteRep;
	
	@Autowired
	VacinaRepository vacinaRep;
	
	Vacina vacinaDoseUnica;
	Vacina vacinaDoseDupla;
	
	Date dataMenor;
	Date dataDoMeio;
	Date dataMaior;
	
	Lote loteVazio;
	Lote proximoLote;
	Lote outroLote;
	
	Lote loteDoseDupla1;
	Lote loteDoseDupla2;
	
	@BeforeEach
	void setUp() {
		loteRep.deleteAll();
		vacinaRep.deleteAll();
		
		this.vacinaDoseUnica = new Vacina("nomeFabricante", "0123456789", new Long(1));
		this.vacinaDoseDupla = new Vacina("nomeFabricante2", "1123456789", new Long(2), new Long(20));
		
		vacinaRep.save(vacinaDoseUnica);
		vacinaRep.save(vacinaDoseDupla);
		
		this.dataMenor = new Date(new Long(1000));
		this.dataDoMeio = new Date(new Long(10000));
		this.dataMaior = new Date(new Long(100000));
		
		this.loteVazio = new Lote(dataMenor, new Long(0), vacinaDoseUnica);
		this.proximoLote = new Lote(dataDoMeio, new Long(100), vacinaDoseUnica);
		this.outroLote = new Lote(dataMaior, new Long(1000), vacinaDoseUnica);
		
		this.loteDoseDupla1 = new Lote(dataMenor, new Long(4), vacinaDoseDupla);
		this.loteDoseDupla2 = new Lote(dataMaior, new Long(4), vacinaDoseDupla);
		
		loteRep.save(loteVazio);
		loteRep.save(proximoLote);
		loteRep.save(outroLote);
		
		loteRep.save(loteDoseDupla1);
		loteRep.save(loteDoseDupla2);
	}
	
	@Test
	void proximoLoteDoseUnicaTest() {
		Optional<Lote> optLote = loteRep.proximoLotePrimeiraDose(vacinaDoseUnica);
		
		assertEquals(optLote.get(), proximoLote);
	}
	
	@Test
	void proximoLoteDoseDuplaTest1() {
		loteDoseDupla1.removerVacinaPrimeiraDose();
		loteDoseDupla1.removerVacinaPrimeiraDose();
		
		Optional<Lote> optLote = loteRep.proximoLoteSegundaDose(vacinaDoseDupla);
		
		assertEquals(optLote.get(), loteDoseDupla1);
	}
	
	void proximoLoteDoseDuplaTest2() {
		loteDoseDupla1.removerVacinaPrimeiraDose();
		loteDoseDupla1.removerVacinaPrimeiraDose();
		
		Optional<Lote> optLote = loteRep.proximoLotePrimeiraDose(vacinaDoseDupla);
		
		assertEquals(optLote.get(), loteDoseDupla2);
	}
}
