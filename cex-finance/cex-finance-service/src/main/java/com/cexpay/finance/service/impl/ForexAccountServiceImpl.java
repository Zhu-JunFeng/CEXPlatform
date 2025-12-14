package com.cexpay.finance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cexpay.finance.domain.ForexAccount;
import com.cexpay.finance.service.ForexAccountService;
import com.cexpay.finance.mapper.ForexAccountMapper;
import org.springframework.stereotype.Service;

/**
* @author zhujf
* @description 针对表【forex_account(创新交易持仓信息)】的数据库操作Service实现
* @createDate 2025-12-14 16:30:33
*/
@Service
public class ForexAccountServiceImpl extends ServiceImpl<ForexAccountMapper, ForexAccount>
    implements ForexAccountService{

}




