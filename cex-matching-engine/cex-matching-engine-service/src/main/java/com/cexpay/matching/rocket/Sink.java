package com.cexpay.matching.rocket;

import org.springframework.messaging.MessageChannel;
import org.springframework.cloud.stream.annotation.Input;

public interface Sink {

    @Input("order.in")
    MessageChannel messageChannel();
}
