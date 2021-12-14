package com.geminibot.geminibot.repositories;

import com.geminibot.geminibot.entities.constants.ApiKeyType;
import com.geminibot.geminibot.entities.postgres.ApiKey;
import com.geminibot.geminibot.entities.postgres.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
    public ArrayList<ApiKey> findByUser(User user);

    public Optional<ApiKey> findByUserAndType(User user, ApiKeyType type);

}
