package com.cexpay.common.exception;

import com.cexpay.common.constant.ErrorCode;

/**
 * 自定义业务异常
 */
public class BusinessException extends RuntimeException {
    
    private Integer code;
    private String message;
    
    public BusinessException(String message) {
        super(message);
        this.code = ErrorCode.BUSINESS_ERROR;
        this.message = message;
    }
    
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
    
    public Integer getCode() {
        return code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
