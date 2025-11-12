package com.cexpay.wallet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cexpay.wallet.domain.Wallet;
import org.apache.ibatis.annotations.Mapper;

/**
 * 钱包Mapper
 */
@Mapper
public interface WalletMapper extends BaseMapper<Wallet> {
    
}
