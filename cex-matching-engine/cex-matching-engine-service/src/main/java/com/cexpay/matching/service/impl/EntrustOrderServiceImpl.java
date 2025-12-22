package com.cexpay.matching.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cexpay.matching.model.entity.EntrustOrder;
import com.cexpay.matching.service.EntrustOrderService;
import com.cexpay.matching.mapper.EntrustOrderMapper;
import org.springframework.stereotype.Service;

/**
 * @author zhujf
 * @description 针对表【entrust_order(委托订单信息)】的数据库操作Service实现
 * @createDate 2025-12-15 22:15:26
 */
@Service
public class EntrustOrderServiceImpl extends ServiceImpl<EntrustOrderMapper, EntrustOrder>
        implements EntrustOrderService {

}




