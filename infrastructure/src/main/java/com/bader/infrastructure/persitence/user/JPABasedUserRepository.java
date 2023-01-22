package com.bader.infrastructure.persitence.user;

import com.bader.domain.user.model.User;
import com.bader.domain.user.repository.UserRepository;
import com.bader.infrastructure.persitence.user.entity.AuthorityEntity;
import com.bader.infrastructure.persitence.user.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JPABasedUserRepository implements UserRepository {
    private final JPAUserRepository jpaUserRepository;
    private final JPAAuthorityRepository jpaAuthorityRepository;

    public JPABasedUserRepository(JPAUserRepository jpaUserRepository, JPAAuthorityRepository jpaAuthorityRepository) {
        this.jpaUserRepository = jpaUserRepository;
        this.jpaAuthorityRepository = jpaAuthorityRepository;
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = jpaUserRepository.save(new UserEntity(user.getUsername(), user.getPassword(), user.isEnabled()));
        if (user.getAuthorities() != null) {
            List<AuthorityEntity> authorities = user.getAuthorities().stream()
                    .map(a -> new AuthorityEntity(userEntity, a.getAuthority()))
                    .collect(Collectors.toList());
            jpaAuthorityRepository.saveAll(authorities);
        }
        return userEntity.toUser();
    }

    @Override
    public User findById(String username) {
        return jpaUserRepository.findById(username).map(UserEntity::toUser).orElse(null);
    }
}
