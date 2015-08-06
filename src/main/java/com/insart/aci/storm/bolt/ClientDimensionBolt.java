package com.insart.aci.storm.bolt;

import java.util.Map;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.espertech.esper.client.EventBean;
import com.insart.aci.storm.esper.vdw.service.VDWSupportService;
import com.insart.aci.storm.model.ATMTransactionEvent;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

public class ClientDimensionBolt extends EsperBaseBolt {
    private static final long serialVersionUID = 1741541189053812441L;
    
    @Override
    public void prepare(Map stormConf, TopologyContext context) {
	Logger logger = LoggerFactory.getLogger(ClientDimensionBolt.class);
	
	super.prepare(stormConf, context);
	this.esServiceProvider.getEPAdministrator().getConfiguration().addVariable("lookupUtil", VDWSupportService.class, new VDWSupportService());
	this.esServiceProvider.getEPAdministrator().getConfiguration().addEventType("ATMTransactionEvent", ATMTransactionEvent.class);
	this.esServiceProvider.getEPAdministrator().createEPL(this.environment.getProperty("vdw.approach4.create"));
	this.esServiceProvider.getEPAdministrator().createEPL(this.environment.getProperty("vdw.approach4.populate"));
	this.esServiceProvider.getEPAdministrator().createEPL(this.environment.getProperty("vdw.approach4.listen")).addListener((newEvents, oldEvents) ->{
	    if(newEvents != null){
		logger.info("New income events:");
		Stream.of(newEvents).filter((a)->a != null).map(EventBean::getUnderlying).forEach((a) -> logger.info(a.toString()));
	    }
	    if(oldEvents != null){
		logger.info("Old espired events:");
		Stream.of(oldEvents).filter((a)->a != null).map(EventBean::getUnderlying).forEach((a) -> logger.info(a.toString()));
	    }
	});
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
	Object incomeEvent = input.getValueByField("atmTransactionEvent");
	if(incomeEvent != null){
	    this.esServiceProvider.getEPRuntime().sendEvent(((ATMTransactionEvent)incomeEvent));
	}
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    }
}
