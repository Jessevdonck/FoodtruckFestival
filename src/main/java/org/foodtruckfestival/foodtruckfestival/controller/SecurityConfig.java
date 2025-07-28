package org.foodtruckfestival.foodtruckfestival.controller;

import org.foodtruckfestival.foodtruckfestival.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.csrfTokenRepository(new HttpSessionCsrfTokenRepository())).
                        authorizeHttpRequests(requests -> requests

                        // pages that require auth
                        .requestMatchers("/login","/festivals", "/").permitAll()
                        .requestMatchers("/dashboard")
                        .hasAnyRole(Role.USER.name(), Role.ADMIN.name())

                        // rest
                        .requestMatchers("/api/festival/**").permitAll()
                        .requestMatchers("/api/festivals").permitAll()

                        // other stuff
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("/i18n/**").permitAll()
                        .requestMatchers("/static/favicon.ico").permitAll()
                        .requestMatchers("/icons/**").permitAll())
                .formLogin(form -> form.defaultSuccessUrl("/festivals", true)
                        .loginPage("/login")
                        .usernameParameter("username").passwordParameter("password"))
                .exceptionHandling(handling -> handling
                        .accessDeniedPage("/error?errorCode=403&errorMessage=Access+Denied"));

        return http.build();
    }
}