package com.ufcg.psoft.vacinaja.repository;

import com.ufcg.psoft.vacinaja.model.Comorbidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComorbidadeRepository extends JpaRepository<Comorbidade, Long> {

    Optional<Comorbidade> findComorbidadeByNomeComorbidade(String nome);

}
