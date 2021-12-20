package com.geminibot.geminibot.repositories;

import com.geminibot.geminibot.entities.postgres.Transfer;
import com.geminibot.geminibot.entities.postgres.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
    public ArrayList<Transfer> findAllByUser(User user);

    public Transfer findOneByUser(User user);

    public Optional<Transfer> findFirstByUserOrderByTimestampmsDesc(User user);
//
//    @Query(value = "select distinct(currency), sum(amount) as sumamount from transfers where type = 'Deposit' AND user_id = ?1 group by currency", nativeQuery = true)
//    public ArrayList<TransferSumPerSymbol> getDepositSumsPerSymbol(@Param("userId") Long userId);
//
//
//    @Query(value = "select distinct(currency), sum(amount) as sumamount from transfers where type = 'Withdrawal' AND user_id = ?1 group by currency", nativeQuery = true)
//    public ArrayList<TransferSumPerSymbol> getWithdrawalSumsPerSymbol(@Param("userId") Long userId);


    @Query(value = """
            select
                distinct(currency),
                sum(amount) as sumamount,
                ?2 as type
            from transfers
            WHERE  user_id = ?1
            AND type = ?2
            group by currency, type
            ORDER BY currency
            """, nativeQuery = true)
    public ArrayList<TransferSumPerSymbol> getTransfersPerSymbol(Long userId, String transferType);

    @Query(value = """
            select * from (
                select
                distinct(currency) as Currency,
                sum(CASE type WHEN 'Deposit' THEN amount ELSE -amount END) as amount
                from transfers
                WHERE  user_id = 4282
                group by ROLLUP(currency)
            ) as rollup
            WHERE rollup.Currency is not null
            """, nativeQuery = true)
    public ArrayList<TransferRollupPerSymbol> getTransferRollupPerSymbol(Long userId);

    // needed to prevent duplicates because of gemini's crappy API
    public Optional<Transfer> findByTypeAndAdvancedAndTimestampmsAndEidAndCurrencyAndAmountAndMethod(String type,
                                                                                                             String advanced,
                                                                                                             BigInteger timestampms,
                                                                                                             BigInteger eid,
                                                                                                             String currency,
                                                                                                             double amount,
                                                                                                             String method);


    public static interface TransferSumPerSymbol {

        String getCurrency();

        BigDecimal getSumamount();

        String getType();
    }

    public static interface TransferRollupPerSymbol {
        String getCurrency();

        BigDecimal getAmount();
    }
}