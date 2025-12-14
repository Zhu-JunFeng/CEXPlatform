package com.cexpay.finance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cexpay.finance.domain.Coin;
import com.cexpay.finance.service.CoinService;
import com.cexpay.finance.mapper.CoinMapper;
import org.springframework.stereotype.Service;

/**
* @author zhujf
* @description 针对表【coin(币种配置信息)】的数据库操作Service实现
* @createDate 2025-12-14 16:30:33
*/
@Service
public class CoinServiceImpl extends ServiceImpl<CoinMapper, Coin>
    implements CoinService{

}




