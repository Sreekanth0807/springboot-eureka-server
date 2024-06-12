package com.example.UserTrackService.repository;


import com.example.UserTrackService.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserTrackRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserIdAndPassword(String userId, String password);
}
