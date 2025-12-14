package com.cexpay.finance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cexpay.finance.domain.CoinServer;
import com.cexpay.finance.service.CoinServerService;
import com.cexpay.finance.mapper.CoinServerMapper;
import org.springframework.stereotype.Service;

/**
* @author zhujf
* @description 针对表【coin_server(监测当前服务器Ip状态)】的数据库操作Service实现
* @createDate 2025-12-14 16:30:33
*/
@Service
public class CoinServerServiceImpl extends ServiceImpl<CoinServerMapper, CoinServer>
    implements CoinServerService{

}




