package com.cexpay.matching.disruptor;

import com.lmax.disruptor.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Disruptor异常处理,Disruptor 默认吞异常
 */
@Slf4j
public class DisruptorHandlerException implements ExceptionHandler {

    /**
     * Handler 某个 EventHandler 在处理 sequence=l 的 event=o 时抛异常了
     * Disruptor 会继续往下跑，Disruptor 会继续往下跑，后续 event 照常消费
     * “悄悄丢了一笔订单”
     *
     * @param throwable
     * @param sequence
     * @param o
     */
    @Override
    public void handleEventException(Throwable throwable, long sequence, Object o) {
        log.error("EventException,Exception = {}, sequence = {}, event = {}", throwable.getMessage(), sequence, o);
    }

    /**
     * Handler 启动失败
     *
     * @param throwable
     */
    @Override
    public void handleOnStartException(Throwable throwable) {
        log.error("OnStartException, Exception = {}", throwable.getMessage());
        throw new RuntimeException(throwable);
    }

    /**
     * Handler 启动失败
     *
     * @param throwable
     */
    @Override
    public void handleOnShutdownException(Throwable throwable) {
        log.error("OnShutdownException, Exception = {}", throwable.getMessage());
    }


}
