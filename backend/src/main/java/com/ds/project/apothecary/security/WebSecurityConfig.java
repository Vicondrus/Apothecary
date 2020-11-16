package com.ds.project.apothecary.security;

import com.ds.project.apothecary.enums.UserType;
import com.ds.project.apothecary.security.services.UserDetailsServiceImpl;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * The type Web security config.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig
        extends WebSecurityConfigurerAdapter {
    /**
     * The User details service.
     */
    private final UserDetailsServiceImpl
            userDetailsService;

    /**
     * The Unauthorized handler.
     */
    private final AuthEntryPointJwt
            unauthorizedHandler;

    /**
     * Instantiates a new Web security config.
     *
     * @param pUserDetailsService  the user details service
     * @param pUnauthorizedHandler the unauthorized handler
     */
    public WebSecurityConfig(
            final UserDetailsServiceImpl pUserDetailsService,
            final AuthEntryPointJwt pUnauthorizedHandler) {
        this.userDetailsService =
                pUserDetailsService;
        this.unauthorizedHandler =
                pUnauthorizedHandler;
    }

    /**
     * Authentication jwt token filter auth token filter.
     *
     * @return the auth token filter
     */
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    /**
     * Configure.
     *
     * @param authenticationManagerBuilder the authentication manager builder
     * @throws Exception the exception
     */
    @Override
    public void configure(
            final AuthenticationManagerBuilder authenticationManagerBuilder)
            throws
            Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * Authentication manager bean authentication manager.
     *
     * @return the authentication manager
     * @throws Exception the exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean()
            throws
            Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Password encoder password encoder.
     *
     * @return the password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configure.
     *
     * @param http the http
     * @throws Exception the exception
     */
    @Override
    protected void configure(final HttpSecurity http)
            throws
            Exception {
        http.cors()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/**", "/websocket-notifications/**",
                        "/pillbox/**")
                .permitAll()
                .antMatchers("/medications/**", "/caregivers/**",
                        "/doctors/**", "/addresses/**")
                .hasAuthority(UserType.DOCTOR.toString())
                .antMatchers(HttpMethod.DELETE, "/patients/**")
                .hasAuthority(UserType.DOCTOR.toString())
                .antMatchers(HttpMethod.POST, "/patients/**")
                .hasAuthority(UserType.DOCTOR.toString())
                .antMatchers(HttpMethod.PUT, "/patients/**")
                .hasAuthority(UserType.DOCTOR.toString())
                .antMatchers(HttpMethod.GET, "/patients/**")
                .hasAnyAuthority(UserType.CAREGIVER.toString(),
                        UserType.DOCTOR.toString())
                .antMatchers(HttpMethod.GET, "/information/**")
                .hasAuthority(UserType.PATIENT.toString())
                .anyRequest()
                .authenticated();

        http.addFilterBefore(
                authenticationJwtTokenFilter(),
                UsernamePasswordAuthenticationFilter.class);
    }

    //    @Bean
    //    CorsConfigurationSource corsConfigurationSource() {
    //        CorsConfiguration configuration = new CorsConfiguration();
    //        configuration.setAllowedOrigins(Collections.singletonList("*"));
    //        configuration.setAllowedMethods(Arrays.asList("GET","POST",
    //        "OPTIONS"));
    //        UrlBasedCorsConfigurationSource source = new
    //        UrlBasedCorsConfigurationSource();
    //        source.registerCorsConfiguration("/**", configuration);
    //        return source;
    //    }
}
