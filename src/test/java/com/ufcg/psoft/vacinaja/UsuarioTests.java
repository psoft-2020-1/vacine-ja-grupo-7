package com.ufcg.psoft.vacinaja;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.ufcg.psoft.vacinaja.model.Cidadao;
import com.ufcg.psoft.vacinaja.model.Comorbidade;
import com.ufcg.psoft.vacinaja.model.Funcionario;
import com.ufcg.psoft.vacinaja.model.Usuario;

@SpringBootTest
public class UsuarioTests {

	Usuario usuario;
	
	@BeforeEach
	void setUp() {
		usuario = new Usuario("carlos.ribeiro@ccc.ufcg.edu.br", "qwerty123");
	}
	
	@Test
	void adicionaPermissaoDeAdministradorTest() {
		try {
			usuario.adicionaPermissaoAdministrador();
		
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void cadastroDeCidadaoTest() {
		try {
			Cidadao cidadao = new Cidadao("nome", "endereco", "12345678910", "numeroCartaoSus", new Date() , "telefone", "profissao", new ArrayList<Comorbidade>());
			
			usuario.adicionaCadastroCidadao(cidadao);
		
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void cadastroDeFuncionario() {
		try {
			Cidadao cidadao = new Cidadao("nome", "endereco", "12345678910", "numeroCartaoSus", new Date() , "telefone", "profissao", new ArrayList<Comorbidade>());
			
			usuario.adicionaCadastroCidadao(cidadao);
			
			Funcionario funcionario = new Funcionario("12345678910", "cargo", "localDeTrabalho");
		
			usuario.adicionaCadastroFuncionario(funcionario);
		
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void adicionaCadastroFuncionarioSemCadastroCidadaoTest() {
		try {
			Funcionario funcionario = new Funcionario("12345678910", "cargo", "localDeTrabalho");
			
			usuario.adicionaCadastroFuncionario(funcionario);
			
			fail();
			
		} catch (Exception e) {}
	}
	
	@Test
	void adicionaPermissaoFuncionarioTest() {
		try {
			Cidadao cidadao = new Cidadao("nome", "endereco", "12345678910", "numeroCartaoSus", new Date() , "telefone", "profissao", new ArrayList<Comorbidade>());
			
			usuario.adicionaCadastroCidadao(cidadao);
			
			Funcionario funcionario = new Funcionario("12345678910", "cargo", "localDeTrabalho");
		
			usuario.adicionaCadastroFuncionario(funcionario);
		
			usuario.adicionaPermissaoFuncionario();
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void adicionaPermissaoFuncionarioSemCadastroTest() {
		try {
			usuario.adicionaPermissaoFuncionario();
			
			fail();
		} catch (Exception e) {}
	}
}