package com.cexpay.matching.match;

import com.cexpay.matching.domain.Order;
import com.cexpay.matching.domain.OrderBooks;

/**
 * 撮合/交易
 */
public interface MatchService {

    /**
     * 订单撮合交易
     * @param order
     */
    void match(OrderBooks orderBooks, Order order);

}
