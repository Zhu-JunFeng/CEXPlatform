package com.cexpay.finance.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 监测当前服务器Ip状态
 * @TableName coin_server
 */
@TableName(value ="coin_server")
@Data
public class CoinServer {
    /**
     * 自增id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 钱包服务器ip
     */
    private String rpcIp;

    /**
     * 钱包服务器ip
     */
    private String rpcPort;

    /**
     * 服务是否运行 0:正常,1:停止
     */
    private Integer running;

    /**
     * 钱包服务器区块高度
     */
    private Long walletNumber;

    /**
     * 
     */
    private String coinName;

    /**
     * 备注信息
     */
    private String mark;

    /**
     * 真实区块高度
     */
    private Long realNumber;

    /**
     * 修改时间
     */
    private Date lastUpdateTime;

    /**
     * 创建时间
     */
    private Date created;
}