package com.cexpay.finance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cexpay.finance.domain.CoinWithdraw;
import com.cexpay.finance.service.CoinWithdrawService;
import com.cexpay.finance.mapper.CoinWithdrawMapper;
import org.springframework.stereotype.Service;

/**
* @author zhujf
* @description 针对表【coin_withdraw(数字货币提现记录)】的数据库操作Service实现
* @createDate 2025-12-14 16:30:33
*/
@Service
public class CoinWithdrawServiceImpl extends ServiceImpl<CoinWithdrawMapper, CoinWithdraw>
    implements CoinWithdrawService{

}




