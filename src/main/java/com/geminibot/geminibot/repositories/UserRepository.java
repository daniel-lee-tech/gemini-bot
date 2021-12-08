package com.geminibot.geminibot.repositories;

import com.geminibot.geminibot.entities.postgres.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);

    public User findByActivationToken(UUID activationToken);
}
