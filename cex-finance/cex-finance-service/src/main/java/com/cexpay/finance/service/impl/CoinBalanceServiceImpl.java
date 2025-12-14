package com.cexpay.finance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cexpay.finance.domain.CoinBalance;
import com.cexpay.finance.service.CoinBalanceService;
import com.cexpay.finance.mapper.CoinBalanceMapper;
import org.springframework.stereotype.Service;

/**
* @author zhujf
* @description 针对表【coin_balance(币种余额)】的数据库操作Service实现
* @createDate 2025-12-14 16:30:33
*/
@Service
public class CoinBalanceServiceImpl extends ServiceImpl<CoinBalanceMapper, CoinBalance>
    implements CoinBalanceService{

}




