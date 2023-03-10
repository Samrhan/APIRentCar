package com.ssa.adapters.persitence.IAM;

import com.ssa.adapters.persitence.IAM.entity.AuthorityEntity;
import com.ssa.adapters.persitence.IAM.entity.UserEntity;
import com.ssa.domain.IAM.model.User;
import com.ssa.domain.IAM.ports.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JPABasedUserRepository implements UserRepository {
    private final JPAUserRepository jpaUserRepository;
    private final JPAAuthorityRepository jpaAuthorityRepository;

    public JPABasedUserRepository(JPAUserRepository jpaUserRepository, JPAAuthorityRepository jpaAuthorityRepository) {
        this.jpaUserRepository = jpaUserRepository;
        this.jpaAuthorityRepository = jpaAuthorityRepository;
    }

    private User save(User user) {
        UserEntity userEntity = jpaUserRepository.save(new UserEntity(user.getUsername(), user.getPassword(), user.isEnabled()));
        if (user.getAuthorities() != null) {
            List<AuthorityEntity> authorities = user.getAuthorities().stream()
                    .map(a -> new AuthorityEntity(userEntity, a.getAuthority()))
                    .collect(Collectors.toList());
            jpaAuthorityRepository.saveAll(authorities);
        }
        return userEntity.toModel();
    }

    @Override
    public User findById(String username) {
        return jpaUserRepository.findById(username).map(UserEntity::toModel).orElse(null);
    }

    @Override
    public Optional<User> createUser(User user) {
        Optional<UserEntity> existingUser = jpaUserRepository.findById(user.getUsername());
        if (existingUser.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(this.save(user));
    }
}
