package com.ufcg.psoft.vacinaja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.psoft.vacinaja.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, String> {}