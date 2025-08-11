package com.vocab.identity.repository;

import com.vocab.identity.entity.User;
import java.util.Optional;
import org.springframework.data.repository.query.Param;

public interface UserQueryRepository {

    Optional<User> findByUsernameWithRoles(@Param("username") String username);
}
