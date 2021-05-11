package com.ufcg.psoft.vacinaja.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import com.ufcg.psoft.vacinaja.exceptions.ValidacaoTokenException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.PrematureJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class TokenFilter extends GenericFilterBean {

	public final static int TOKEN_INDEX = 7;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;

		String header = req.getHeader("Authorization");

		if (header == null || !header.startsWith("Bearer ")) {
			throw new ValidacaoTokenException("ErroValidaçãoToken: Token inexistente ou mal formatado!");
		}

		String token = header.substring(TOKEN_INDEX);

		try {
			Jwts.parser().setSigningKey("chave secreta").parseClaimsJws(token).getBody();
		} catch (SignatureException | ExpiredJwtException | MalformedJwtException | PrematureJwtException
				| UnsupportedJwtException | IllegalArgumentException e) {
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
			return;
		}

		chain.doFilter(request, response);
	}

}
