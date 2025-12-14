package com.cexpay.finance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cexpay.finance.domain.CoinConfig;
import com.cexpay.finance.service.CoinConfigService;
import com.cexpay.finance.mapper.CoinConfigMapper;
import org.springframework.stereotype.Service;

/**
* @author zhujf
* @description 针对表【coin_config(币种配置信息)】的数据库操作Service实现
* @createDate 2025-12-14 16:30:33
*/
@Service
public class CoinConfigServiceImpl extends ServiceImpl<CoinConfigMapper, CoinConfig>
    implements CoinConfigService{

}




