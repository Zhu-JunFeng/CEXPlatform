package com.cexpay.matching.disruptor;

import com.lmax.disruptor.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Disruptor异常处理,Disruptor 默认吞异常
 */
@Slf4j
public class DisruptorHandlerException implements ExceptionHandler {

    @Override
    public void handleEventException(Throwable throwable, long l, Object o) {
        log.error("EventException,Exception = {}, queue = {}, event = {}", throwable.getMessage(), l, o);
    }

    @Override
    public void handleOnStartException(Throwable throwable) {
        log.error("OnStartException, Exception = {}", throwable.getMessage());
    }

    @Override
    public void handleOnShutdownException(Throwable throwable) {
        log.error("OnShutdownException, Exception = {}", throwable.getMessage());
    }


}
