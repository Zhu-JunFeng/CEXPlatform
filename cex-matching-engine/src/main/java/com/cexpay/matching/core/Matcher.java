package com.cexpay.matching.core;

import com.cexpay.matching.domain.Order;
import com.cexpay.matching.domain.Trade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class Matcher {
    private final AtomicLong tradeIdSeq = new AtomicLong(1);

    public List<Trade> match(OrderBook book, Order taker) {
        List<Trade> trades = new ArrayList<>();
        Map<BigDecimal, PriceLevel> opposite = taker.getSide() == Order.Side.BUY ? book.getAsks() : book.getBids();

        while (taker.hasRemaining() && !opposite.isEmpty()) {
            Map.Entry<BigDecimal, PriceLevel> bestEntry = opposite.entrySet().iterator().next();
            PriceLevel bestLevel = bestEntry.getValue();
            BigDecimal bestPrice = bestEntry.getKey();

            // price check: buy can match if bestPrice <= taker.price (for limit)
            if (!canMatch(taker, bestPrice)) break;

            while (!bestLevel.isEmpty() && taker.hasRemaining()) {
                Order maker = bestLevel.peek();
                BigDecimal tradeQty = taker.remaining().min(maker.remaining());
                BigDecimal tradePrice = bestPrice;

                Trade t = new Trade(tradeIdSeq.getAndIncrement(), taker.getSymbol(),
                        taker.getSide() == Order.Side.BUY ? taker.getOrderId() : maker.getOrderId(),
                        taker.getSide() == Order.Side.SELL ? taker.getOrderId() : maker.getOrderId(),
                        tradePrice, tradeQty, System.currentTimeMillis());

                // apply fills
                taker.fill(tradeQty);
                maker.fill(tradeQty);
                trades.add(t);

                if (maker.getStatus() == Order.Status.FILLED) {
                    bestLevel.poll();
                }
            }

            if (bestLevel.isEmpty()) {
                opposite.remove(bestPrice);
            }
        }

        // if taker still has remaining and is LIMIT, add to book
        if (taker.hasRemaining() && taker.getType() == Order.Type.LIMIT) {
            book.addOrderDirect(taker);
        }

        return trades;
    }

    private boolean canMatch(Order taker, BigDecimal bestPrice) {
        if (taker.getType() == Order.Type.MARKET) return true;
        if (taker.getSide() == Order.Side.BUY) {
            return bestPrice.compareTo(taker.getPrice()) <= 0;
        } else {
            return bestPrice.compareTo(taker.getPrice()) >= 0;
        }
    }
}