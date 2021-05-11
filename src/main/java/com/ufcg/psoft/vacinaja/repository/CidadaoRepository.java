package com.ufcg.psoft.vacinaja.repository;

import com.ufcg.psoft.vacinaja.model.Cidadao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CidadaoRepository extends JpaRepository<Cidadao, String> {

    Optional<Cidadao> findCidadaoByCpf(String cpf);
}
