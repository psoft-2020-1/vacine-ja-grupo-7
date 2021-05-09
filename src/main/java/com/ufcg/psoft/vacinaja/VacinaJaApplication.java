package com.ufcg.psoft.vacinaja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.ufcg.psoft.vacinaja.security.TokenFilter;

@SpringBootApplication
public class VacinaJaApplication {

	@Bean
	public FilterRegistrationBean<TokenFilter> filterJwt() {
		FilterRegistrationBean<TokenFilter> filterRB = new FilterRegistrationBean<TokenFilter>();
		filterRB.setFilter(new TokenFilter());
		filterRB.addUrlPatterns("/api/usuario");
		return filterRB;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(VacinaJaApplication.class, args);
	}

}
