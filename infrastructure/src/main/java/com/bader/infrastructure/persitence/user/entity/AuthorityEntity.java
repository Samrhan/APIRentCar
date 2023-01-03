package com.bader.infrastructure.persitence.user.entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "authorities")
public class AuthorityEntity implements Serializable {

    @Id
    @ManyToOne()
    @JoinColumn(name = "username", referencedColumnName = "username")
    private UserEntity user;

    @Column(name = "authority", nullable = false)
    private String authority;

    public AuthorityEntity() {
    }
}
