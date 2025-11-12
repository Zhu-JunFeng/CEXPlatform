package com.cexpay.websocket.config;

import com.cexpay.websocket.handler.TradeWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * WebSocket配置
 */
@Configuration
public class WebSocketConfig {
    
    @Autowired
    private TradeWebSocketHandler tradeWebSocketHandler;
    
    public SimpleUrlHandlerMapping webSocketHandlerMapping() {
        Map<String, WebSocketHandler> map = new HashMap<>();
        map.put("/ws/trade", tradeWebSocketHandler);
        
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setUrlMap(map);
        mapping.setOrder(1);
        return mapping;
    }
}
