package com.alkemy.ong.auth.config;

import com.alkemy.ong.auth.filter.JwtRequestFilter;
import com.alkemy.ong.auth.service.UserDetailsCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                .authorizeRequests().antMatchers("/auth/*").permitAll()
                .antMatchers("/auth/register/**").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/storage/*").hasAuthority("ADMIN")

                //Users
                .antMatchers(GET, "/users/list").hasAuthority("ADMIN")
                .antMatchers(POST, "/user/save/**").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(PATCH, "/users/{id}").hasAuthority("USER")
                .antMatchers(DELETE, "/users/{id}").hasAuthority("USER")
                .antMatchers(GET,"/users/auth/me").hasAuthority( "USER")

                //Categories
                .antMatchers(GET, "/categories").hasAuthority("ADMIN")
                .antMatchers(PUT, "/categories/{id}").hasAuthority("ADMIN")
                .antMatchers(POST, "/categories/create").hasAuthority("ADMIN")
                .antMatchers(DELETE, "/categories/{id}").hasAuthority("ADMIN")
                .antMatchers(GET,"/categories/{id}").hasAuthority("ADMIN")

                //Organizations
                .antMatchers(GET, "/organization/public").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(PUT, "/organization/public/**").hasAuthority("ADMIN")

                //Activities
                .antMatchers(PUT,"/activities/{id}").hasAuthority("ADMIN")
                 .antMatchers(POST, "/activities").hasAuthority("ADMIN")

                //News
                .antMatchers(GET, "/news/{id}").hasAuthority("ADMIN")
                .antMatchers(POST, "/news").hasAuthority("ADMIN")
                .antMatchers(PUT, "/news").hasAuthority("ADMIN")
                .antMatchers(DELETE, "/news/{id}").hasAuthority("ADMIN")



                .antMatchers(POST, "/news").hasAuthority("ADMIN")
                .antMatchers(PUT, "/news").hasAuthority("ADMIN")


                //Slides
                .antMatchers(GET, "/slides").hasAuthority("ADMIN")
                .antMatchers(DELETE, "/slides/{id}").hasAuthority("ADMIN")
                .antMatchers(PUT, "/slides/{id}").hasAuthority("ADMIN")
                .antMatchers(GET, "/slides/{id}").hasAuthority("ADMIN")

                //Contacts
                .antMatchers(POST, "/contacts").hasAuthority("USER")
                .antMatchers(GET, "/contacts").hasAuthority("ADMIN")
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}