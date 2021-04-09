package com.tsubin.webapp.main.configuration;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.web.cors.CorsConfigurationSource;

@RunWith(MockitoJUnitRunner.class)
public class SecurityConfigTests {
	@InjectMocks
	private SecurityConfig config;
	
	@Mock
	private KeycloakAuthenticationProcessingFilter filter;
	
	@Mock 
	private KeycloakAuthenticationProvider provider;
	
//	@Mock
//	private AuthenticationEntryPoint aep;
	
	@Test
	public void test1() {
		assertTrue(config.keycloakAuthenticationProcessingFilterRegistrationBean(filter) instanceof FilterRegistrationBean);
	}
	
	@Test
	public void test2() {
		assertTrue(config.keycloakAuthenticationProvider() instanceof KeycloakAuthenticationProvider);
	}
	
	@Test
	public void test3() {
		assertTrue(config.sessionAuthenticationStrategy() instanceof SessionAuthenticationStrategy);
	}

//	@Test
//	public void test4() throws Exception {
//		assertTrue(config.exceptionTranslationFilter() instanceof ExceptionTranslationFilter);
//	}
	
	@Test
	public void test5() {
		assertTrue(config.allowUrlEncodedHttpFirewall() instanceof HttpFirewall);
	}
	
	@Test
	public void test6() {
		assertTrue(config.corsConfigurationSource() instanceof CorsConfigurationSource);
	}
}
