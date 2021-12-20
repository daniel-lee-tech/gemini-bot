package com.geminibot.geminibot.repositories;

import com.geminibot.geminibot.entities.postgres.Trade;
import com.geminibot.geminibot.entities.postgres.Transfer;
import com.geminibot.geminibot.entities.postgres.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    public ArrayList<Trade> findAllByUser(User user);

    public Trade findOneByUser(User user);

    public Optional<Trade> findFirstByUserOrderByTimestampmsDesc(User user);


    // this is really UGLY and stupid I KNOW.
    // but i can't trust gemini to give accurate data without polling repeat data.
    // i know this is really dumb but i have to think of a different approach.
    public Optional<Trade> findByPriceAndAmountAndTimestampAndTimestampmsAndTypeAndAggressorAndFeeCurrencyAndFeeAmountAndTidAndOrderIdAndExchangeAndAuctionFillAndClearingFillAndSymbolAndGeminiBreak(
            BigDecimal price,
     BigDecimal amount,
     BigInteger timestamp,
     BigInteger timestampms,
     String type,
     boolean aggressor,
     String feeCurrency,
     BigDecimal feeAmount,
     BigInteger tid,
     BigInteger orderId,
     String exchange,
     boolean isAuctionFill,
     boolean isClearingFill,
     String symbol,
     String geminiBreak);
//
//    @Query(value = """
//        SELECT
//            DISTINCT(trades.symbol),
//                    sum_amount as sumAmount,
//                    sum_fee as sumFee,
//                    t.fee_currency as feeCurrency,
//                    average_price as averagePrice,
//                    symbol.base_currency as baseCurrency,
//                    symbol.quote_currency as quoteCurrency,
//                    t.type
//        FROM (
//                 SELECT
//                     symbol,
//                     SUM(amount) AS sum_amount,
//                     SUM(fee_amount) AS sum_fee,
//                     SUM(price * amount) / SUM(amount) AS average_price,
//                     fee_currency,
//                     type
//                 FROM trades
//                 WHERE
//                     (gemini_break IS NULL OR gemini_break = 'manual')
//                   AND user_id = ?1
//                 GROUP BY trades.symbol, trades.type, fee_currency
//             ) t
//        JOIN trades ON trades.symbol = t.symbol
//        JOIN symbol ON t.symbol = symbol.symbol
//        WHERE
//            (gemini_break IS NULL OR gemini_break = 'manual')
//        ORDER BY symbol, type
//
//        """, nativeQuery = true)
//    public ArrayList<TradeSumPerSymbol> getTradeAggregatePerSymbol(Long userId);


    // NOTE: THIS QUERY IS NOT ACCURATE BECAUSE IT DOES NOT CALCULATE FEES
    // NOTE: YOU MUST RUN THE FEE QUERY AND COMBINE THE OUTPUTS FOR ACCURATE PRICING
    @Query(value = """
           SELECT DISTINCT(trades.symbol),
                   sum_amount as baseAmount,
                   average_price as averagePrice,
                   symbol.base_currency as baseCurrency,
                   symbol.quote_currency as quoteCurrency,
                    (average_price * sum_amount * -1) as quoteAmount
            FROM (
                 SELECT symbol,
                        SUM(CASE when type = 'Buy' then amount else -amount end)  AS sum_amount,
                        SUM(fee_amount)                   AS sum_fee,
                        SUM(price * amount) / SUM(amount) AS average_price,
                        fee_currency,
                        type
                 FROM trades
                 WHERE (gemini_break IS NULL OR gemini_break = 'manual')
                   AND user_id = 4282
                 GROUP BY trades.symbol, trades.type, fee_currency
             ) t
                 JOIN trades ON trades.symbol = t.symbol
                 JOIN symbol ON t.symbol = symbol.symbol
            WHERE (gemini_break IS NULL OR gemini_break = 'manual')
            ORDER BY symbol

        """, nativeQuery = true)
    public ArrayList<TradeSumPerSymbol> getTradeAggregatePerSymbol(Long userId);




    @Query(value = """
      select fee_currency as feeCurrency, sum(fee_amount) as feeAmount from trades where user_id = ?1 group by fee_currency
        """, nativeQuery = true)
    public ArrayList<TotalTradeFees> getAllFeesBySymbol(Long userId);

    Optional<Trade> findByTid(BigInteger tid);

    public static interface TotalTradeFees {
        public String getFeeCurrency();

        public BigDecimal getFeeAmount();
    }



    public static interface TradeSumPerSymbol {

        public String getSymbol() ;

        public BigDecimal getSumAmount();

        public BigDecimal getBaseAmount();
        public BigDecimal getQuoteAmount();

        public BigDecimal getAveragePrice() ;

//        public String getType();

//        public String getFeeCurrency();

        public String getBaseCurrency();
        public String getQuoteCurrency();

    }
}
