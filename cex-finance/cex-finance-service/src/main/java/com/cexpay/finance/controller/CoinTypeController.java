package com.cexpay.finance.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cexpay.common.model.PageResult;
import com.cexpay.finance.domain.CoinType;
import com.cexpay.finance.service.CoinTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/coinType")
public class CoinTypeController {


    @Autowired
    private CoinTypeService coinTypeService;

    public PageResult<CoinType> findByPage(@RequestBody Page<CoinType> page) {
        return null;
    }
}
