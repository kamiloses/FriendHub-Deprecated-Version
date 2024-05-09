package com.application.friendhub.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity//todo sprawdzić czy wymagane jest
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(request->request.requestMatchers("/home").authenticated().

                requestMatchers("/friendHub/secondStep").permitAll().anyRequest().permitAll()

        ).formLogin(login->login.loginPage("/friendHub/login").permitAll());
         SecurityContextHolder securityContextHolder = new SecurityContextHolder();
        SecurityContext context = SecurityContextHolder.getContext();

        return http.build();}





/*
@Bean
    PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
}
*/
@Bean
    PasswordEncoder passwordEncoder(){

//todo Bcrypt
    return NoOpPasswordEncoder.getInstance();
}

}
