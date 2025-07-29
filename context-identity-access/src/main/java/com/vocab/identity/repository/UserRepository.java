package com.vocab.identity.repository;

import com.vocab.identity.entity.Role;
import com.vocab.identity.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserQueryRepository {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    List<User> findAllByRoles(List<Role> roles);

    List<User> findAllByRolesAndEnabled(List<Role> roles, boolean enabled);

}
