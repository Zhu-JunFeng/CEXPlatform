package com.cexpay.matching.boot;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cexpay.matching.disruptor.DisruptorTemplate;
import com.cexpay.matching.model.Order;
import com.cexpay.matching.model.entity.EntrustOrder;
import com.cexpay.matching.mapper.EntrustOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 启动时从数据库读取委托单数据 放到ringBuffer
 */
@Component
public class DataLoadCmdRunner implements CommandLineRunner {

    @Autowired
    private DisruptorTemplate disruptorTemplate;

    @Autowired
    private EntrustOrderMapper entrustOrderMapper;

    /**
     * 项目启动后就会执行
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        // 查询委托单数据
        LambdaQueryWrapper<EntrustOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EntrustOrder::getStatus, 0)
                .orderByAsc(EntrustOrder::getCreated);
        List<EntrustOrder> entrustOrders = entrustOrderMapper.selectList(wrapper);
        if (CollUtil.isEmpty(entrustOrders)) {
            return;
        }
        // 把委托单放入 ringBuffer
        for (EntrustOrder entrustOrder : entrustOrders) {
            disruptorTemplate.onData(Order.entrustOrder2Order(entrustOrder));
        }


    }


}
