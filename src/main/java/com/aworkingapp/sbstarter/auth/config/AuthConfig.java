package com.aworkingapp.sbstarter.auth.config;

import com.aworkingapp.sbstarter.auth.AuthoritiesConstants;
import com.aworkingapp.sbstarter.auth.Http401UnauthorizedEntryPoint;
import com.aworkingapp.sbstarter.auth.service.TokenAuthenticationService;
import com.aworkingapp.sbstarter.auth.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by chen on 7/11/15.
 * auth config file
 */
@Configuration
public class AuthConfig  extends WebSecurityConfigurerAdapter {
    public AuthConfig() {
        super(true);
    }

    /**
     * {@link UserDetailsService}
     */
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    private Http401UnauthorizedEntryPoint authenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint).and()
                    .anonymous()
                .and()
                    .servletApi()
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
				//anonymous resource requests
                    .antMatchers("/").permitAll()
                    .antMatchers("/**").permitAll()
                    .antMatchers("/health").permitAll()

                    .antMatchers("/api-docs").permitAll()

                    .antMatchers("/api/**").permitAll()
                    .antMatchers("/api/pub").permitAll()
                    .antMatchers("/api/users/**").permitAll()
                    .antMatchers("/favicon.ico").permitAll()
                    .antMatchers("/resources/**").permitAll()

                    //anonymous for login url
                    .antMatchers(HttpMethod.POST, "/api/login").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/register").permitAll()

                    //admin
                    .antMatchers("/admin/**").hasAuthority(AuthoritiesConstants.ADMIN)

                    //all other request need to be authenticated
                    .anyRequest().authenticated().and()

                    // custom JSON based authentication by POST of {"username":"<name>","password":"<password>"} which sets the token header upon authentication
                    .addFilterBefore(new StatelessLoginFilter("/api/login", tokenAuthenticationService, userDetailsService, authenticationManager()), UsernamePasswordAuthenticationFilter.class)

                    // custom Token based authentication based on the header previously given to the client
                    .addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return userDetailsService;
    }
}
