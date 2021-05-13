package com.ufcg.psoft.vacinaja.repository;

import com.ufcg.psoft.vacinaja.model.RegistroVacinacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistroRepository extends JpaRepository<RegistroVacinacao, String> {



}
