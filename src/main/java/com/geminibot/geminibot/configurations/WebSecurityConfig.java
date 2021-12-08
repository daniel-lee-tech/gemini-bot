package com.geminibot.geminibot.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // disabling csrf here, you should enable it before using in production
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        // for react index.html to render properly
                        HttpMethod.GET,
                        "/", "/index*", "/static/**","/static/css/**", "/*.js", "/*.json", "/*.ico")
                .permitAll()
                .antMatchers( "/index", "/health/**", "/test/**", "/user/register", "/user/activate/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }
}
