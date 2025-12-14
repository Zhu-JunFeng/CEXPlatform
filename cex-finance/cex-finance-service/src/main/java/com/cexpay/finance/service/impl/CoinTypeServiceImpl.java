package com.cexpay.finance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cexpay.finance.domain.CoinType;
import com.cexpay.finance.service.CoinTypeService;
import com.cexpay.finance.mapper.CoinTypeMapper;
import org.springframework.stereotype.Service;

/**
* @author zhujf
* @description 针对表【coin_type(币种类型)】的数据库操作Service实现
* @createDate 2025-12-14 16:30:33
*/
@Service
public class CoinTypeServiceImpl extends ServiceImpl<CoinTypeMapper, CoinType>
    implements CoinTypeService{

}




