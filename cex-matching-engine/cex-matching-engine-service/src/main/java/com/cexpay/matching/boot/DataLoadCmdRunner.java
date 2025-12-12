package com.cexpay.matching.boot;

import com.cexpay.matching.disruptor.DisruptorTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        // 查询委托单数据

        // 把委托单放入ringBuffer


    }

}
