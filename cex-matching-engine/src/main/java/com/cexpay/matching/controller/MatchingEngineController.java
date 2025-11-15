package com.cexpay.matching.controller;

import com.cexpay.matching.domain.Order;
import com.cexpay.matching.domain.Trade;
import com.cexpay.matching.service.IMatchingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/matching")
public class MatchingEngineController {

	@Autowired
	private IMatchingService matchingService;

	/**
	 * 提交订单并返回成交列表（可能为空）
	 */
	@PostMapping("/orders")
	public ResponseEntity<List<Trade>> submitOrder(@RequestBody Order order) {
		List<Trade> trades = matchingService.submitOrder(order);
		return ResponseEntity.ok(trades);
	}

	/**
	 * 撤单（symbol + orderId）
	 */
	@PostMapping("/orders/{symbol}/{orderId}/cancel")
	public ResponseEntity<Map<String, Object>> cancelOrder(@PathVariable String symbol, @PathVariable Long orderId) {
		boolean ok = matchingService.cancelOrder(orderId, symbol);
		return ResponseEntity.ok(Map.of("success", ok));
	}

	/**
	 * 获取订单簿快照
	 */
	@GetMapping("/orderbook/{symbol}")
	public ResponseEntity<Object> getOrderBook(@PathVariable String symbol) {
		Object snapshot = matchingService.getOrderBook(symbol);
		return ResponseEntity.ok(snapshot);
	}
}
