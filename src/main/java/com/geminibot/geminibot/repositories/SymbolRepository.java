package com.geminibot.geminibot.repositories;

import com.geminibot.geminibot.entities.constants.ApiKeyType;
import com.geminibot.geminibot.entities.postgres.ApiKey;
import com.geminibot.geminibot.entities.postgres.Symbol;
import com.geminibot.geminibot.entities.postgres.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface SymbolRepository extends JpaRepository<Symbol, Long> {
    public Optional<Symbol> findBySymbol(String symbol);
}
