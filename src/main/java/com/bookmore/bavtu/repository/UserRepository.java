package com.bookmore.bavtu.repository;

import com.bookmore.bavtu.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends MongoRepository<User, String> {
    @Query("{username:'?0'}")
    public  User findByUsername(@Param("username") String username);

    @Query("{email:'?0'}")
    public  User findByEmail(@Param("email") String email);
}
