package com.bader.infrastructure.persitence.user.entity;

import com.bader.domain.user.model.Authority;
import com.bader.domain.user.model.User;
import org.springframework.data.annotation.PersistenceCreator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity implements Serializable {
    @Id
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @OneToMany(targetEntity = AuthorityEntity.class, mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AuthorityEntity> authorities = new ArrayList<>();

    @PersistenceCreator
    public UserEntity(String username, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public UserEntity() {
    }

    public List<AuthorityEntity> getAuthorities() {
        return authorities;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public User toModel() {
        List<Authority> authorities = new ArrayList<>();
        for (AuthorityEntity authorityEntity : this.authorities) {
            authorities.add(authorityEntity.toAuthority());
        }
        return new User(username, password, enabled, authorities);
    }

    public void setAuthorities(List<AuthorityEntity> authorities) {
        this.authorities = authorities;
    }
}
