SELECT DISTINCT(trades.symbol),
               sum_amount,
               sum_fee,
               t.fee_currency,
               average_price,
               t.type
FROM (
         SELECT symbol,
                SUM(amount)                       AS sum_amount,
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
WHERE (gemini_break IS NULL OR gemini_break = 'manual')
ORDER BY symbol, type
;

-- getting all trades per symbol and joining with symbols table

SELECT DISTINCT(trades.symbol),
               sum_amount,
               sum_fee,
               t.fee_currency,
               average_price,
               symbol.base_currency,
               symbol.quote_currency,
               t.type
FROM (
         SELECT symbol,
                SUM(amount)                       AS sum_amount,
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
ORDER BY symbol, type
;


-- get all trades
select distinct(currency),
               sum(amount) as sumamount,
               ?2          as type
from transfers
WHERE user_id = ?1
  AND type = ?2
group by currency, type
ORDER BY currency
;

select distinct(currency),
               sum(CASE type WHEN 'Deposit' THEN amount ELSE -amount END) as sumamount
from transfers
WHERE user_id = 4282
group by ROLLUP (currency)
;


SELECT DISTINCT(symbol),
--        SUM(sum_amount * average_price) as currencySum,
        SUM(sum_fee) as feeSum

FROM (
         SELECT DISTINCT(trades.symbol),
                        sum_amount,
                        sum_fee,
                        t.fee_currency,
                        average_price
         FROM (
                  SELECT symbol,
                         SUM(CASE WHEN type = 'Buy' then amount ELSE -amount END) AS sum_amount,
                         SUM(fee_amount)                                          AS sum_fee,
                         SUM(price * amount) / SUM(amount)                        AS average_price,
                         fee_currency,
                         type
                  FROM trades
                  WHERE (gemini_break IS NULL OR gemini_break = 'manual')
                    AND user_id = 4282
                  GROUP BY trades.symbol, trades.type, fee_currency
              ) t
                  JOIN trades ON trades.symbol = t.symbol
         WHERE (gemini_break IS NULL OR gemini_break = 'manual')
         ORDER BY symbol
) as combined
GROUP BY combined.symbol
ORDER BY combined.symbol
;



SELECT DISTINCT(trades.symbol),
               SUM(sum_amount) as baseAmount,
               symbol.base_currency,
               symbol.quote_currency,
                SUM(average_price * sum_amount * -1) as quoteAmount
FROM (
         SELECT symbol,
                SUM(CASE when type = 'Buy' then amount else -amount end)                       AS sum_amount,
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
GROUP BY trades.symbol, symbol.base_currency, symbol.quote_currency
ORDER BY symbol
;
