package com.cexpay.matching.match;


import cn.hutool.core.bean.BeanUtil;
import com.cexpay.matching.disruptor.OrderEvent;
import com.cexpay.matching.disruptor.OrderEventHandler;
import com.cexpay.matching.domain.OrderBooks;
import com.lmax.disruptor.EventHandler;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Set;

@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(MatchEngineProperties.class)
public class MatchEngineAutoConfiguration {

    private MatchEngineProperties matchEngineProperties;


    @Bean
    public EventHandler<OrderEvent>[] eventHandlers() {
        Map<String, MatchEngineProperties.CoinScale> symbols = matchEngineProperties.getSymbols();
        EventHandler<OrderEvent>[] eventHandlers = new EventHandler[symbols.size()];
        int i = 0;
        for (Map.Entry<String, MatchEngineProperties.CoinScale> entry : symbols.entrySet()) {
            String symbol = entry.getKey();
            MatchEngineProperties.CoinScale coinScale = entry.getValue();
            OrderBooks orderBooks;
            if (BeanUtil.isNotEmpty(coinScale)) {
                orderBooks = new OrderBooks(symbol, coinScale.getBaseCoinScale(), coinScale.getBaseCoinScale());
            } else {
                orderBooks = new OrderBooks(symbol);
            }
            eventHandlers[i++] = new OrderEventHandler(orderBooks);
        }

        return eventHandlers;
    }
}
