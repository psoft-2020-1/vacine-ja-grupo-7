package com.ufcg.psoft.vacinaja.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.vacinaja.enums.PermissaoLogin;
import com.ufcg.psoft.vacinaja.exceptions.AdministradorInvalidoException;
import com.ufcg.psoft.vacinaja.exceptions.ValidacaoTokenException;
import com.ufcg.psoft.vacinaja.model.Usuario;

@Service
public class AdministradorServiceImpl implements AdministradorService {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private JWTService jwtService;
	
	private void validaString(String entrada) {
		if ((entrada == null) || entrada.trim().equals("")) {
			throw new AdministradorInvalidoException("Login inválido");
		}
	}
	
	@Override
	public Usuario aprovaFuncionario(String emailFuncionario, String token) {
		this.validaString(emailFuncionario);
		if (jwtService.verificaPermissao(token, PermissaoLogin.ADMINISTRADOR)) {
			Usuario usuario = usuarioService.getUsuario(emailFuncionario);
			usuario.adicionaPermissaoFuncionario();
			return usuarioService.salvarUsuario(usuario);
		}
		throw new ValidacaoTokenException("ErroPermissaoToken: Token passado não tem permissão para a operação.");
	}

}
