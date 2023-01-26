package com.ssa.ports.web.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.stream.Collectors;

public class JwtAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private final String roleClaim;

    public JwtAuthoritiesConverter(String roleClaim) {
        this.roleClaim = roleClaim;
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        return jwt
                .getClaimAsStringList(this.roleClaim)
                .stream()
                .map(role -> String.format("ROLE_%s", role))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}