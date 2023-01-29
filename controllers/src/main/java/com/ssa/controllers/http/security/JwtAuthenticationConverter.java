package com.ssa.controllers.http.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtAuthoritiesConverter jwtAuthoritiesConverter;

    public JwtAuthenticationConverter(JwtAuthoritiesConverter jwtAuthoritiesConverter) {
        this.jwtAuthoritiesConverter = jwtAuthoritiesConverter;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        return new JwtAuthenticationToken(jwt, jwtAuthoritiesConverter.convert(jwt));
    }
}