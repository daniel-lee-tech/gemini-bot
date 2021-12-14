package com.geminibot.geminibot.repositories;

import com.geminibot.geminibot.entities.postgres.Transfer;
import com.geminibot.geminibot.entities.postgres.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
    public ArrayList<Transfer> findAllByUser(User user);

    public Transfer findOneByUser(User user);

    public Optional<Transfer> findFirstByUserOrderByTimestampmsDesc(User user);
}
