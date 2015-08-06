package com.insart.aci.storm.configuration;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.soda.StreamSelector;
import com.insart.aci.storm.esper.vdw.CouchbaseVirtualDataWindowFactory;

@Configuration
public class EsperConfiguration {
    private static final Logger logger;

    @Resource
    private Environment environment;

    private EPServiceProvider epServiceProvider;

    static {
	logger = LoggerFactory.getLogger(EsperConfiguration.class);
    }

    @Bean
    public EPServiceProvider getEPServiceProvider() {
	com.espertech.esper.client.Configuration configuration = new com.espertech.esper.client.Configuration();
	configuration.addPlugInVirtualDataWindow("couchbase", "couchbasevdw", CouchbaseVirtualDataWindowFactory.class.getName());
	configuration.getEngineDefaults().getExecution().setPrioritized(true);
	configuration.getEngineDefaults().getStreamSelection().setDefaultStreamSelector(StreamSelector.RSTREAM_ISTREAM_BOTH);
	epServiceProvider = EPServiceProviderManager.getDefaultProvider(configuration);

	return epServiceProvider;
    }
}
