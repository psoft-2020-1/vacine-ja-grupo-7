package com.ufcg.psoft.vacinaja.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Entidade que representa um lote de vacinas no sistema.
 * O lote possui uma data de validade, um número total de doses e uma vacina associada.
 * Internamente, o lote reserva as doses para primeira e segunda aplicação, caso a vacina possua essa característica.
 * 
 * Então na prática, um lote com dez unidades de uma vacina de duas doses, é capaz de vacinar cinco pessoas.
 *
 * Em lotes associados a vacinas de apenas uma aplicação, há sempre zero doses reservadas para a segunda aplicação.
 * 
 */
@Entity
public class Lote {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	private Date dataDeValidade;
	
	private Long reservadasPrimeiraDose;
	
	private Long reservadasSegundaDose;
	
	@ManyToOne
	private Vacina vacina;

	public Lote() {}
	
	public Lote(Date dataDeValidade, Long numeroDeDoses, Vacina vacina) {
		this.dataDeValidade = dataDeValidade;
		this.vacina = vacina;
		reservaVacinas(numeroDeDoses);
	}
	
	private void reservaVacinas(Long numeroDeDoses) {
		if (vacina.ehDoseDupla()) {
			this.reservadasPrimeiraDose = (numeroDeDoses / 2);
			this.reservadasSegundaDose = numeroDeDoses / 2;
		
		} else {
			this.reservadasPrimeiraDose = numeroDeDoses;
			this.reservadasSegundaDose = new Long(0);
		}
	}
	
	public Long getQuantidadePessoasVacinaveis() {
		return reservadasPrimeiraDose;
	}
	
	public void removerVacinaPrimeiraDose() {
		this.reservadasPrimeiraDose--;
	}
	
	public void removerVacinaSegundaDose() {
		if (vacina.ehDoseDupla()) {
			this.reservadasSegundaDose--;
		}
	}
	
	public Long getId() {
		return id;
	}

	public Date getDataDeValidade() {
		return dataDeValidade;
	}

	public void setDataDeValidade(Date dataDeValidade) {
		this.dataDeValidade = dataDeValidade;
	}

	public Vacina getVacina() {
		return vacina;
	}

	public void setVacina(Vacina vacina) {
		this.vacina = vacina;
	}

	public Long getReservadasPrimeiraDose() {
		return reservadasPrimeiraDose;
	}

	public void setReservadasPrimeiraDose(Long reservadasPrimeiraDose) {
		this.reservadasPrimeiraDose = reservadasPrimeiraDose;
	}

	public Long getReservadasSegundaDose() {
		return reservadasSegundaDose;
	}

	public void setReservadasSegundaDose(Long reservadasSegundaDose) {
		this.reservadasSegundaDose = reservadasSegundaDose;
	}
	
	@Override
	public String toString() {
		return "Lote [id=" + id + ", dataDeValidade=" + dataDeValidade + ", reservadasPrimeiraDose="
				+ reservadasPrimeiraDose + ", reservadasSegundaDose=" + reservadasSegundaDose + ", vacina=" + vacina
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lote other = (Lote) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
