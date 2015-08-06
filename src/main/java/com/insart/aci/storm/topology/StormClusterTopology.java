package com.insart.aci.storm.topology;

import com.insart.aci.storm.bolt.ClientDimensionBolt;
import com.insart.aci.storm.spout.StormSpout;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;

public class StormClusterTopology {
    public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {
	TopologyBuilder builder = new TopologyBuilder();

	builder.setSpout("Spout", new StormSpout(), 1);
	builder.setBolt("Bolt1", new ClientDimensionBolt(), 1).shuffleGrouping("Spout");
	Config conf = new Config();
	conf.setDebug(false);

	try {
	    StormSubmitter.submitTopology("com", conf, builder.createTopology());
	} catch (AlreadyAliveException e) {
	    e.printStackTrace();
	} catch (InvalidTopologyException e) {
	    e.printStackTrace();
	}
    }
}