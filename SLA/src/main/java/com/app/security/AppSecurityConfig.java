package com.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.security.exceptions.AppAccessDeniedHandler;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AppAuthFilter appAuthFilter;

    @Autowired
    private AppAuthenticationProvider appAuthenticationProvider;

    @Autowired
    private AppAuthenticationEntryPoint appAuthenticationEntryPoint;

    @Override
    public void configure(AuthenticationManagerBuilder auth)  throws Exception {
        auth.authenticationProvider(appAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
		        .antMatchers(
		                HttpMethod.GET,
		                "/testapp/*",
		                "/testapp/favicon.ico",
		                "/testapp/*.html",
		                "/testapp/**/*.css",
		                "/testapp/**/*.js"
		        ).permitAll()
                .antMatchers("/login","/usermanagement/**/*")
                .permitAll()
                .antMatchers("/profile/**/*")
                .hasAuthority("USER")
                .antMatchers("/consumer/*")
                .hasAuthority("CONSUMER")
                .antMatchers("/provider/*")
                .hasAuthority("CONSUMER")
                .and()
                .addFilterBefore(appAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(new AppAccessDeniedHandler())
                .authenticationEntryPoint(appAuthenticationEntryPoint);
    }
}
