package com.ufcg.psoft.vacinaja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.psoft.vacinaja.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {}
