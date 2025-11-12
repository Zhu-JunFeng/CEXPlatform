package com.cexpay.matching.service;

import com.cexpay.matching.core.OrderBook;

public interface SnapshotService {
    void saveSnapshot(OrderBook orderBook);
    OrderBook loadSnapshot(String symbol);
}