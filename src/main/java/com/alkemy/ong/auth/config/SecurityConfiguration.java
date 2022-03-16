package com.alkemy.ong.auth.config;

import com.alkemy.ong.auth.filter.JwtRequestFilter;
import com.alkemy.ong.auth.service.UserDetailsCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsCustomService userDetailsCustomService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    //ARRAYS THE ENDPOINT VERB
    private static final String[] USER = {
            "/users/auth/me", "/news/{id}/comments", "/news?page=", "/testimonials?page=","/news","/testimonials",
            "/comments", "/contacts", "/members","/members/{id}","/users/{id}"
    };

    private static final String[] ANY_ROLE = {
            "/organization/public/{id}", "/members?page=","/members","/user/save/**","/comments/{id}"
    };

    /*
    Method to encrypt password
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
    /*
    Configuration to set where the UserDetailsService will be.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsCustomService);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests().antMatchers("/auth/*", "/swagger-ui/**","/v2/api-docs/**", "/swagger-resources/**").permitAll()
                .antMatchers("/storage/*").hasAuthority("ADMIN")
                // ANY USER
                .antMatchers(HttpMethod.GET, ANY_ROLE).hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, ANY_ROLE).hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, ANY_ROLE).hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE, ANY_ROLE).hasAnyAuthority("ADMIN", "USER")
                //USER
                .antMatchers(HttpMethod.GET, USER).hasAuthority( "USER")
                .antMatchers(HttpMethod.POST, USER).hasAuthority("USER")
                .antMatchers(HttpMethod.PUT, USER).hasAuthority("USER")
                .antMatchers(HttpMethod.PATCH, USER).hasAuthority("USER")
                .antMatchers(HttpMethod.DELETE, USER).hasAuthority("USER")
                //ADMIN
                .antMatchers(HttpMethod.GET, "/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/**").hasAuthority("ADMIN")

                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}