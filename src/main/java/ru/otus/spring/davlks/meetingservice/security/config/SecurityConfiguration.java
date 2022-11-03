package ru.otus.spring.davlks.meetingservice.security.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/registration", "/", "/login").permitAll()
                .and()
                .authorizeRequests().antMatchers("/admin").hasRole("ADMIN")
                .and()
                .authorizeRequests().antMatchers("/**").authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/start")
        ;
    }

}
