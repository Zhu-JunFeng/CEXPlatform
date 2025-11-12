package com.cexpay.matching.service.impl;

import com.cexpay.matching.core.OrderBook;
import com.cexpay.matching.service.OrderBookManager;
import com.cexpay.matching.service.SnapshotService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SnapshotServiceImpl implements SnapshotService {
    private final OrderBookManager manager;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public SnapshotServiceImpl(OrderBookManager manager) {
        this.manager = manager;
    }

    @Override
    public void saveSnapshot(OrderBook orderBook) {
        // simple placeholder: in production serialize to Redis/S3/FS
        System.out.println("[SNAPSHOT] save for " + orderBook.getSymbol());
    }

    @Override
    public OrderBook loadSnapshot(String symbol) {
        // placeholder: try to read from storage
        return manager.getOrCreate(symbol);
    }

    public void startPeriodicSnapshot(long intervalSeconds) {
        scheduler.scheduleAtFixedRate(() -> {
            // iterate books and snapshot
            manager.getOrCreate("BTCUSDT"); // ensure exists
            // in real impl iterate all books
            System.out.println("[SNAPSHOT] periodic task run");
        }, intervalSeconds, intervalSeconds, TimeUnit.SECONDS);
    }
}
