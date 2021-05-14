package com.ufcg.psoft.vacinaja.repository;

import com.ufcg.psoft.vacinaja.model.Cidadao;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.psoft.vacinaja.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    public Optional<Usuario> getUsuarioByCadastroCidadao(Cidadao cidadao);

}
