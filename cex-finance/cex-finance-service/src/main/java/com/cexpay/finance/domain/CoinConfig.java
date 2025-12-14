package com.cexpay.finance.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 币种配置信息
 * @TableName coin_config
 */
@TableName(value ="coin_config")
@Data
public class CoinConfig {
    /**
     * 币种ID(对应coin表ID)
     */
    @TableId
    private Long id;

    /**
     * 币种名称
     */
    private String name;

    /**
     * btc-比特币系列；eth-以太坊；ethToken-以太坊代币；etc-以太经典；\r\n\r\n
     */
    private String coinType;

    /**
     * 钱包最低留存的币
     */
    private BigDecimal creditLimit;

    /**
     * 当触发改状态的时候,开始归集
     */
    private BigDecimal creditMaxLimit;

    /**
     * rpc服务ip
     */
    private String rpcIp;

    /**
     * rpc服务port
     */
    private String rpcPort;

    /**
     * rpc用户
     */
    private String rpcUser;

    /**
     * rpc密码
     */
    private String rpcPwd;

    /**
     * 最后一个区块
     */
    private String lastBlock;

    /**
     * 钱包用户名
     */
    private String walletUser;

    /**
     * 钱包密码
     */
    private String walletPass;

    /**
     * 代币合约地址
     */
    private String contractAddress;

    /**
     * context
     */
    private String context;

    /**
     * 最低确认数
     */
    private Integer minConfirm;

    /**
     * 定时任务
     */
    private String task;

    /**
     * 是否可用0不可用,1可用
     */
    private Integer status;

    /**
     * 
     */
    private BigDecimal autoDrawLimit;

    /**
     * 
     */
    private Integer autoDraw;
}