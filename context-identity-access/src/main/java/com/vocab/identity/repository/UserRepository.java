package com.vocab.identity.repository;

import com.vocab.identity.entity.User;
import com.vocab.identity.entity.embedded.EmailAddress;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserQueryRepository {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(EmailAddress email);
}
