package com.cexpay.matching.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum OrderDirection {

    BULL(1, "买入"),
    SELL(2, "卖出");

    private int code;
    private String desc;

    public static OrderDirection getByCode(int code) {
        OrderDirection[] values = values();
        for (OrderDirection value : values) {
            if (value.code == code) {
                return value;
            }
        }
        return null;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
