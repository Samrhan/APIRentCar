package com.bader.infrastructure.persitence.user.entity;


import com.bader.domain.user.model.Authority;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "authorities")
public class AuthorityEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "org.hibernate.type.UUIDCharType")
    UUID id;

    @ManyToOne()
    @JoinColumn(name = "username", referencedColumnName = "username")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;

    @Column(name = "authority", nullable = false)
    private String authority;

    public AuthorityEntity(UserEntity user, String authority) {
        this.user = user;
        this.authority = authority;
    }

    public AuthorityEntity() {
    }

    Authority toAuthority() {
        return new Authority(authority);
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
