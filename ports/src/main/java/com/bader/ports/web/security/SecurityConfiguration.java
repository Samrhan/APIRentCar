package com.bader.ports.web.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.sql.DataSource;
import java.io.IOException;

@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    public static final String ADMIN = "ADMIN";

    public static final String CUSTOMER = "CUSTOMER";

    public static final String SELLER = "SELLER";

    private final SecretKey key;
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final DataSource dataSource;

    private PasswordEncoder passwordEncoder;


    public SecurityConfiguration(
            // Generated using https://stackoverflow.com/questions/33960565/how-to-generate-a-hs512-secret-key-to-use-with-jwt/56934114#56934114
            @Value("classpath:/secret.key") Resource secretKey,
            HandlerExceptionResolver handlerExceptionResolver,
            DataSource dataSource) throws IOException {
        this.key = new SecretKeySpec(StreamUtils.copyToByteArray(secretKey.getInputStream()), "HS256");
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.dataSource = dataSource;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/iam/**").anonymous()
                .and()
                .authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/catalog/**").permitAll() // GET requests don't need auth
                .mvcMatchers(HttpMethod.GET, "/search/**").permitAll()
                .and()
                .authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/catalog/**").authenticated() // All other need auth
                .mvcMatchers(HttpMethod.PUT, "/catalog/**").authenticated() // All other need auth
                .mvcMatchers(HttpMethod.DELETE, "/catalog/**").authenticated() // All other need auth
                .and()
                .authorizeRequests()
                .mvcMatchers("/rental.**").authenticated()
                .and()
                .authorizeRequests()
                .anyRequest().denyAll()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> handlerExceptionResolver.resolveException(request, response, null, authException))
                .accessDeniedHandler((request, response, accessDeniedException) -> handlerExceptionResolver.resolveException(request, response, null, accessDeniedException))
                .and()
                .oauth2ResourceServer()
                .accessDeniedHandler((request, response, accessDeniedException) -> handlerExceptionResolver.resolveException(request, response, null, accessDeniedException))
                .jwt()
                .decoder(jwtDecoder())
                .jwtAuthenticationConverter(new JwtAuthenticationConverter(new JwtAuthoritiesConverter("roles")));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder);

    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withSecretKey(this.key).build();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder encoder() {
        passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder;
    }
}