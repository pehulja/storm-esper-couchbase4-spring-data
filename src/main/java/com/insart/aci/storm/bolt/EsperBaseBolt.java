package com.insart.aci.storm.bolt;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import com.espertech.esper.client.EPServiceProvider;
import com.insart.aci.storm.configuration.ApplicationContextProvider;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.base.BaseBasicBolt;

public abstract class EsperBaseBolt extends BaseBasicBolt {

    private static final long serialVersionUID = 8149418872162130535L;
    private static transient ConfigurableApplicationContext springContext;
    
    @Autowired
    protected EPServiceProvider esServiceProvider;
    
    @Autowired
    protected Environment environment;
    
    @Override
    public void prepare(Map stormConf, TopologyContext context) {
	super.prepare(stormConf, context);
	ApplicationContextProvider.getInstance().getApplicationContext().getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    public void cleanup() {
	super.cleanup();
    }
}