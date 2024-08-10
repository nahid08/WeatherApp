package com.example.proIt.configuration;

import com.example.proIt.security.UserDetailsServiceImpl;
import com.example.proIt.views.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration
        extends VaadinWebSecurity {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/login/**").permitAll().
                    requestMatchers("/registration/**").permitAll();
        });

        http.authenticationProvider(authenticationProvider());
        http.formLogin(formLogin -> {
            formLogin.loginPage("/login");
            formLogin.successHandler(customAuthenticationSuccessHandler);
        });

        super.configure(http);}

    @Override
    public void configure(WebSecurity web) throws Exception {

        super.configure(web);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetailsService);

        return authProvider;
    }

}