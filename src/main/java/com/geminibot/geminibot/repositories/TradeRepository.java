package com.geminibot.geminibot.repositories;

import com.geminibot.geminibot.entities.postgres.Trade;
import com.geminibot.geminibot.entities.postgres.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    public ArrayList<Trade> findAllByUser(User user);

    public Trade findOneByUser(User user);

    public Optional<Trade> findFirstByUserOrderByTimestampmsDesc(User user);
}
