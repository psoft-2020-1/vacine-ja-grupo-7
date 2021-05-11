package com.ufcg.psoft.vacinaja.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.vacinaja.enuns.PermissaoLogin;
import com.ufcg.psoft.vacinaja.exceptions.LoginException;
import com.ufcg.psoft.vacinaja.exceptions.ValidacaoTokenException;
import com.ufcg.psoft.vacinaja.model.Usuario;
import com.ufcg.psoft.vacinaja.security.TokenFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Service
public class JWTService {

	@Autowired
	private UsuarioService usuarioService;

	private final String TOKEN_KEY = "chave secreta";

	public String autenticar(Usuario usuario) {
		usuarioService.validarUsuarioSenhaPermissao(usuario);
		return geraToken(usuario);
	}

	private String geraToken(Usuario usuario) {
		PermissaoLogin permissao;
		if (usuario.isPermissaoCidadao())
			permissao = PermissaoLogin.CIDADAO;
		else if (usuario.isPermissaoFuncionario())
			permissao = PermissaoLogin.FUNCIONARIO;
		else if (usuario.isPermissaoAdministrador())
			permissao = PermissaoLogin.ADMINISTRADOR;
		else
			throw new LoginException("ErroLogin: Nenhuma permissão de usuario selecionada");

		return Jwts.builder().setSubject(usuario.getEmail()).claim("permissao", permissao.getValue())
				.signWith(SignatureAlgorithm.HS512, TOKEN_KEY)
				.setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000)).compact();
	}

	public String getEmailToken(String authorizationHeader) {
		String token = authorizationHeader.substring(TokenFilter.TOKEN_INDEX);
		try {
			return Jwts.parser().setSigningKey(TOKEN_KEY).parseClaimsJws(token).getBody().getSubject();
		} catch (SignatureException e) {
			throw new ValidacaoTokenException("ErroValidacaoToken: Token invalido ou expirado!");
		}
	}
	
	public String getPermissaoToken(String authorizationHeader) {
		String token = authorizationHeader.substring(TokenFilter.TOKEN_INDEX);
		try {
			return (String) Jwts.parser().setSigningKey(TOKEN_KEY).parseClaimsJws(token).getBody().get("permissao");
		} catch (SignatureException e) {
			throw new ValidacaoTokenException("ErroValidacaoToken: Token invalido ou expirado!");
		}
	}
	
	public boolean verificaPermissaoEmail(String token, String email) {
		String emailToken = getEmailToken(token);
		Usuario usuario = usuarioService.getUsuario(emailToken);
		return usuario.getEmail().equals(email);
	}
	
	public boolean verificaPermissao(String token, PermissaoLogin permissao) {
		String emailToken = getEmailToken(token);
		String permissaoToken = getPermissaoToken(token);
		usuarioService.getUsuario(emailToken);
		return permissao.getValue().equals(permissaoToken);
	}

}
