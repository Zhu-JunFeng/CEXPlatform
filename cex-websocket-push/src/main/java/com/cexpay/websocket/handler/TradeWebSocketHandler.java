package com.cexpay.websocket.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

/**
 * WebSocket处理器
 */
@Slf4j
@Component
public class TradeWebSocketHandler implements WebSocketHandler {
    
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.send(
                session.receive()
                        .map(WebSocketMessage::getPayloadAsText)
                        .map(msg -> {
                            log.info("收到消息: {}", msg);
                            return session.textMessage("Echo: " + msg);
                        })
        );
    }
}
