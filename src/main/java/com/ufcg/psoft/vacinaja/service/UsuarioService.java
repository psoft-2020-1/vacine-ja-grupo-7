package com.ufcg.psoft.vacinaja.service;

import java.util.Optional;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.vacinaja.enuns.PermissaoLogin;
import com.ufcg.psoft.vacinaja.model.Usuario;
import com.ufcg.psoft.vacinaja.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private JWTService jwtService;

	public Usuario cadastrarUsuario(Usuario usuario) {
		if (usuarioRepository.findById(usuario.getEmail()).isPresent()) {
			throw new IllegalArgumentException("Id do usuario informado ja existe");
		}
		return usuarioRepository.save(usuario);
	}

	public Usuario getUsuario(String email) {
		Optional<Usuario> optUsuario = usuarioRepository.findById(email);
		if (!optUsuario.isPresent()) {
			throw new IllegalArgumentException("Identificação de usuario informada não cadastrada.");
		}
		return optUsuario.get();
	}

	public Usuario alterarSenha(String email, String novaSenha, String token) throws ServletException {
		Usuario usuario = getUsuario(email);
		if (jwtService.verificaPermissao(token, PermissaoLogin.ADMINISTRADOR)
				|| jwtService.verificaPermissaoEmail(token, email)) {
			usuario.setSenha(novaSenha);
			usuarioRepository.save(usuario);
			return usuario;
		}
		throw new ServletException("Usuario nao tem permissao");

	}

	public Usuario removerUsuario(String email, String token) throws ServletException {
		Usuario usuario = getUsuario(email);
		if (jwtService.verificaPermissao(token, PermissaoLogin.ADMINISTRADOR)
				|| jwtService.verificaPermissaoEmail(token, email)) {
			usuarioRepository.delete(usuario);
			return usuario;
		}
		throw new ServletException("Usuario nao tem permissao");
	}

	public boolean validarUsuarioSenhaPermissao(Usuario usuario) {
		try {
			Usuario usuarioConsulta = getUsuario(usuario.getEmail());
			if (usuarioConsulta.getSenha().equals(usuario.getSenha())) {
				if ((usuario.isPermissaoAdministrador() && !usuarioConsulta.isPermissaoAdministrador())
						|| (usuario.isPermissaoFuncionario() && !usuarioConsulta.isPermissaoFuncionario())
						|| (usuario.isPermissaoCidadao() && !usuarioConsulta.isPermissaoCidadao()))
					return false;
				return true;
			}
			return false;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
}
