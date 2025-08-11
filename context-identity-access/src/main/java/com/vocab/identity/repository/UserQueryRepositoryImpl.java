package com.vocab.identity.repository;

import static com.vocab.identity.entity.QUser.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.vocab.identity.entity.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<User> findByUsernameWithRoles(String username) {

        return Optional.ofNullable(queryFactory
                .selectFrom(user)
                .leftJoin(user.roles).fetchJoin()
                .where(user.username.eq(username))
                .fetchOne()
        );
    }
}
