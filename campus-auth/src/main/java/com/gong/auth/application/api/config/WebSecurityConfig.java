package com.gong.auth.application.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {



    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Resource
    private DaoAuthenticationProviderCustom authenticationProviderCustom;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProviderCustom);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http
//                .authorizeRequests()
//                .antMatchers("/face/contrast", "/wx/**","/registered/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .csrf().disable();
        http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll();

//        http.formLogin();
    }
//
//    @Bean
//    public UserDetailsService userDetailsService(){
//        return new InMemoryUserDetailsManager(
//                User.withUsername("admin").password(passwordEncoder().encode("admin")).authorities("all").build());
//    }

}
