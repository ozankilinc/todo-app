package com.todo.app.userservice.repository;

import com.todo.app.userservice.entity.User;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.N1qlSecondaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByUserId(String userId);

    boolean existsUserByEmail(String email);

}
