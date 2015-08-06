package com.insart.aci.storm.topology;

import com.insart.aci.storm.bolt.ClientDimensionBolt;
import com.insart.aci.storm.spout.StormSpout;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;

public class StormLocalTopology {
    public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {
	TopologyBuilder builder = new TopologyBuilder();

	builder.setSpout("Spout", new StormSpout(), 1);
	builder.setBolt("Bolt1", new ClientDimensionBolt(), 1).shuffleGrouping("Spout");

	Config conf = new Config();
	conf.setDebug(false);

	LocalCluster cluster = new LocalCluster();

	cluster.submitTopology("StormEsperTopology", conf, builder.createTopology());

	try {
	    Thread.sleep(1000000000000l);
	} catch (Exception exception) {
	    System.out.println("Thread interrupted exception : " + exception);
	}

	cluster.killTopology("StormEsperTopology");
	cluster.shutdown();
    }
}