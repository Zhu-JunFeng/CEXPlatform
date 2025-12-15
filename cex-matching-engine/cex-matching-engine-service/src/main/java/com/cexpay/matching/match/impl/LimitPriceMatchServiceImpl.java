package com.cexpay.matching.match.impl;

import com.cexpay.matching.domain.Order;
import com.cexpay.matching.domain.OrderBooks;
import com.cexpay.matching.match.MatchService;
import com.cexpay.matching.match.MatchServiceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import static com.cexpay.matching.enums.MatchStrategy.LIMIT_PRICE;

@Slf4j
@Service
public class LimitPriceMatchServiceImpl implements MatchService, InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        MatchServiceFactory.addMatchService(LIMIT_PRICE, this);
    }

    @Override
    public void match(OrderBooks orderBooks, Order order) {
//        log.info("Matching order {}", order);
        orderBooks.addOrder(order);
    }

}
