package com.ufcg.psoft.vacinaja.repository;

import com.ufcg.psoft.vacinaja.model.RegistroVacinacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RegistroRepository extends JpaRepository<RegistroVacinacao, String> {


}
