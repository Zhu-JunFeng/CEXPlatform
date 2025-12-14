package com.cexpay.finance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cexpay.finance.domain.Account;
import com.cexpay.finance.service.AccountService;
import com.cexpay.finance.mapper.AccountMapper;
import org.springframework.stereotype.Service;

/**
* @author zhujf
* @description 针对表【account(用户财产记录)】的数据库操作Service实现
* @createDate 2025-12-14 16:30:33
*/
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account>
    implements AccountService{

}




