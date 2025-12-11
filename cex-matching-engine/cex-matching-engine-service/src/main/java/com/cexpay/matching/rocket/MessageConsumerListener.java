package com.cexpay.matching.rocket;

import cn.hutool.json.JSONUtil;
import com.cexpay.matching.disruptor.DisruptorTemplate;
import com.cexpay.matching.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageConsumerListener {


    @Autowired
    private DisruptorTemplate disruptorTemplate;

    @StreamListener("order.in")
    public void handler(Order order) {
        log.info("MessageConsumerListener接受到委托单 order = {}", JSONUtil.toJsonStr(order));
        disruptorTemplate.onData(order);
    }
}
