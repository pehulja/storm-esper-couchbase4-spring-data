package com.insart.aci.storm.esper.vdw;

import java.util.Collections;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.espertech.esper.client.hook.VirtualDataWindow;
import com.espertech.esper.client.hook.VirtualDataWindowContext;
import com.espertech.esper.client.hook.VirtualDataWindowFactory;
import com.espertech.esper.client.hook.VirtualDataWindowFactoryContext;

public class CouchbaseVirtualDataWindowFactory implements VirtualDataWindowFactory {

    private static final Logger log = LoggerFactory.getLogger(CouchbaseVirtualDataWindowFactory.class);

    /**
     * @param host
     * @param backetName
     * @param backetPassword
     */
    public CouchbaseVirtualDataWindowFactory() {
	super();
    }

    @Override
    public void initialize(VirtualDataWindowFactoryContext virtualDataWindowFactoryContext) {
	log.debug("", virtualDataWindowFactoryContext);
    }

    @Override
    public VirtualDataWindow create(VirtualDataWindowContext context) {
	return new CouchbaseVirtualDataWindow(context);
    }

    @Override
    public void destroyAllContextPartitions() {
	log.debug("destroyAllContextPartitions()");
    }

    @Override
    public Set<String> getUniqueKeyPropertyNames() {
	return Collections.singleton("id");
    }
}