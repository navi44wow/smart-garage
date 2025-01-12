package com.example.smartgarage.config;

import com.example.smartgarage.services.SmartGarageUserService;


import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final SmartGarageUserService userDetailsService;

    public SecurityConfig(SmartGarageUserService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();

        http.authorizeRequests().
                requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/", "/contactUs", "/home/services", "/whyUs", "/users/login", "/password/forgot", "/resetPassword/**").permitAll()
                .antMatchers("/api/users/**", "/api/vehicles/**", "/api/services/**", "/api/brands/**", "/api/models/**", "/api/visits/**", "/api/send-email**").permitAll().
                antMatchers("/employee/**").hasRole("EMPLOYEE")
                .antMatchers("/visits/**").hasRole("EMPLOYEE")
                .antMatchers("/services/**").hasRole("EMPLOYEE")
                .antMatchers("/vehicles/**").hasRole("EMPLOYEE")
                .antMatchers("/brands/**").hasRole("EMPLOYEE")
                .antMatchers("/carModels/**").hasRole("EMPLOYEE").
                antMatchers("/**").authenticated().
                and().
                formLogin()
                .loginPage("/users/login").
                usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY).
                passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY).
                failureForwardUrl("/users/login-error")
                .successHandler((req, res, auth) -> {// override the default URL for specific roles
                    if (auth.getAuthorities().stream()
                            .anyMatch(a -> a.getAuthority().equals("ROLE_NOT_REGISTERED_CUSTOMER"))) {
                        res.sendRedirect("/users/update/" + auth.getName());
                    } else if (auth.getAuthorities().stream()
                            .anyMatch(a -> a.getAuthority().equals("ROLE_FORGOT_PASSWORD_CUSTOMER"))) {
                        res.sendRedirect("/resetPassword/" + auth.getName());
                    } else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_EMPLOYEE"))) {
                        res.sendRedirect("/employee");
                    } else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"))) {
                        res.sendRedirect("/customer");
                    }
                }).
                and().
                logout()
                .logoutUrl("/logout").
                logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                userDetailsService(userDetailsService).
                passwordEncoder(passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public Authentication authentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth;
    }
}
