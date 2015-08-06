package com.insart.aci.storm.esper.vdw;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.hook.VirtualDataWindow;
import com.espertech.esper.client.hook.VirtualDataWindowContext;
import com.espertech.esper.client.hook.VirtualDataWindowEvent;
import com.espertech.esper.client.hook.VirtualDataWindowLookup;
import com.espertech.esper.client.hook.VirtualDataWindowLookupContext;
import com.insart.aci.storm.configuration.ApplicationContextProvider;
import com.insart.aci.storm.model.TransactionEvent;
import com.insart.aci.storm.persistance.repository.GenericTransactionRepository;

@Component(value = "CouchbaseVirtualDataWindow")
@Scope(value = "prototype")
public class CouchbaseVirtualDataWindow implements VirtualDataWindow {
    private static final Logger log = LoggerFactory.getLogger(CouchbaseVirtualDataWindowFactory.class);
    private VirtualDataWindowContext context;

    @Autowired
    GenericTransactionRepository eventRepository;

    @Autowired
    private CouchbaseVirtualDataWindowKeyValueLookup couchbaseVirtualDataWindowKeyValueLookup;

    public CouchbaseVirtualDataWindow(VirtualDataWindowContext context) {
	this.context = context;
	ApplicationContextProvider.getInstance().getApplicationContext().getAutowireCapableBeanFactory().autowireBean(this);
    }

    public VirtualDataWindowLookup getLookup(VirtualDataWindowLookupContext lookupContext) {
	this.couchbaseVirtualDataWindowKeyValueLookup.setContext(context);
	this.couchbaseVirtualDataWindowKeyValueLookup.setLookupContext(lookupContext);
	return this.couchbaseVirtualDataWindowKeyValueLookup;
    }

    @Override
    public void handleEvent(VirtualDataWindowEvent virtualDataWindowEvent) {
	log.info("handleEvent()");
    }

    public void update(EventBean[] newData, EventBean[] oldData) {
	try {
	    if (newData != null && newData.length > 0) {
		for (EventBean eventBean : newData) {
		    eventRepository.save((TransactionEvent) eventBean.getUnderlying());
		}
	    }
	    if (oldData != null && oldData.length > 0) {
		for (EventBean deleteBean : oldData) {
		    eventRepository.delete((TransactionEvent) deleteBean.getUnderlying());
		}
	    }
	} catch (Exception ex) {
	    log.error("", ex);
	}
	context.getOutputStream().update(newData, oldData);
    }

    public void destroy() {
    }

    /*
     * Should return all events related to some specific event Type
     * 
     * @see com.espertech.esper.client.hook.VirtualDataWindow#iterator()
     */
    @Override
    public Iterator<EventBean> iterator() {
	List<EventBean> obtainedBeans = null;
	obtainedBeans = Collections.<EventBean> emptyList();

	try {
	    Iterable<TransactionEvent> obtainedEvents = eventRepository.findAll();
	    if (obtainedEvents == null) {
		obtainedBeans = Collections.<EventBean> emptyList();
	    } else {
		obtainedBeans = new LinkedList<>();
		for (TransactionEvent genericEvent : obtainedEvents) {
		    obtainedBeans.add(context.getEventFactory().wrap(genericEvent));
		}
	    }
	} catch (Exception ex) {
	    log.error("", ex);
	    obtainedBeans = Collections.<EventBean> emptyList();
	}
	return obtainedBeans.iterator();
    }
}