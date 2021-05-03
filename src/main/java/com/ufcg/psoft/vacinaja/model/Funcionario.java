package com.ufcg.psoft.vacinaja.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.ufcg.psoft.vacinaja.dto.FuncionarioDTO;

@Entity
public class Funcionario {
	@Id
	private String cpf;
	
	private String cargo;
	
	private String localDeTrabalho;
	
	public Funcionario() {}
	
	public Funcionario(String cpf, String cargo, String localDeTrabalho) {
		this.cpf = cpf;
		this.cargo = cargo;
		this.localDeTrabalho = localDeTrabalho;
	}

	public Funcionario(FuncionarioDTO funcionarioDTO) {
		this(funcionarioDTO.getCpfFuncionario(), 
				funcionarioDTO.getCargoFuncionario(), 
				funcionarioDTO.getLocalDeTrabalhoFuncionario());
	
	}

	public String getCpf() {
		return cpf;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getLocalDeTrabalho() {
		return localDeTrabalho;
	}

	public void setLocalDeTrabalho(String localDeTrabalho) {
		this.localDeTrabalho = localDeTrabalho;
	}
}