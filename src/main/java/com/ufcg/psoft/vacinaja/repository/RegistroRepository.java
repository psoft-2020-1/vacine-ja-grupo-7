package com.ufcg.psoft.vacinaja.repository;

import com.ufcg.psoft.vacinaja.model.RegistroVacinacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroRepository extends JpaRepository<RegistroVacinacao, String> {


}
