package com.nepxion.discovery.plugin.example.zuul.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.StrategyEntity;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.zuul.filter.ZuulStrategyFilterResolver;
import com.netflix.zuul.ZuulFilter;

public class MyZuulFilter extends ZuulFilter {
    @Autowired
    private PluginAdapter pluginAdapter;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        String routeVersion = getRouteVersionFromConfig();
        // String routeVersion = getRouteVersionFromCustomer();

        System.out.println("Route Version=" + routeVersion);

        // ͨ������������·��Headerͷ����Ϣ����ȡ�����棨Postman���ϵ����ã���ȫ��·���ݵ������
        ZuulStrategyFilterResolver.setHeader(DiscoveryConstant.N_D_VERSION, routeVersion);

        return null;
    }

    // ��Զ���������Ļ��߱��������ļ���ȡ�汾·�����á������Զ���������ģ���ֵ�ᶯ̬�ı�
    protected String getRouteVersionFromConfig() {
        RuleEntity ruleEntity = pluginAdapter.getRule();
        if (ruleEntity != null) {
            StrategyEntity strategyEntity = ruleEntity.getStrategyEntity();
            if (strategyEntity != null) {
                return strategyEntity.getVersionValue();
            }
        }

        return null;
    }

    // �Զ���汾·������
    protected String getRouteVersionFromCustomer() {
        return "{\"discovery-springcloud-example-a\":\"1.0\", \"discovery-springcloud-example-b\":\"1.0\", \"discovery-springcloud-example-c\":\"1.0;1.2\"}";
    }
}