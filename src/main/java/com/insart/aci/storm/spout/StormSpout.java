package com.insart.aci.storm.spout;

import java.util.Map;

import com.insart.aci.storm.model.mock.TransactionEventGenerator;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class StormSpout extends BaseRichSpout {
    private SpoutOutputCollector spoutOutputCollector;

    private static final long serialVersionUID = 6040967478858767120L;

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
	this.spoutOutputCollector = collector;
    }

    @Override
    public void nextTuple() {
	spoutOutputCollector.emit(new Values(TransactionEventGenerator.random()), "atmTransactionEvent");
	try {
	    Thread.sleep(500);
	} catch (InterruptedException e) {
	}
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
	declarer.declare(new Fields("atmTransactionEvent"));
    }
}
