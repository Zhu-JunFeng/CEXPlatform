package com.cexpay.gateway.controller;

import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Slf4j
@ResponseBody
@RestController
@RequestMapping("/gw")
public class GateWayController {

    // 获取当前系统的限流策略
    @RequestMapping("/flow/routes")
    public Set<GatewayFlowRule> getFlowRoutes() {
        return GatewayRuleManager.getRules();
    }


    // 获取api分组信息
    @RequestMapping("/api/groups")
    public Set<ApiDefinition> getGroups() {
        return GatewayApiDefinitionManager.getApiDefinitions();
    }
}
