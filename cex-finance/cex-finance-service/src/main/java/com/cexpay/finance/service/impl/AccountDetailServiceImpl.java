package com.cexpay.finance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cexpay.finance.domain.AccountDetail;
import com.cexpay.finance.service.AccountDetailService;
import com.cexpay.finance.mapper.AccountDetailMapper;
import org.springframework.stereotype.Service;

/**
* @author zhujf
* @description 针对表【account_detail(资金账户流水)】的数据库操作Service实现
* @createDate 2025-12-14 16:30:33
*/
@Service
public class AccountDetailServiceImpl extends ServiceImpl<AccountDetailMapper, AccountDetail>
    implements AccountDetailService{

}




