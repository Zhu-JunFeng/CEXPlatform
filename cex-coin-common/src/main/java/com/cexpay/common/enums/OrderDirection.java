package com.cexpay.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum OrderDirection {

    BUY(1, "买入"),
    SELL(2, "卖出");

    private Integer code;
    private String desc;

    public static OrderDirection getByCode(Integer code) {
        OrderDirection[] values = values();
        for (OrderDirection value : values) {
            if (Objects.equals(value.code, code)) {
                return value;
            }
        }
        return null;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
