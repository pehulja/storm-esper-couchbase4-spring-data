package com.insart.aci.storm.esper.vdw.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.insart.aci.storm.configuration.ApplicationContextProvider;
import com.insart.aci.storm.model.ATMTransactionEvent;
import com.insart.aci.storm.persistance.repository.ATMTransactionRepository;

public class VDWSupportService {
    @Autowired
    private ATMTransactionRepository atmTransactionRepository;
    
    private static Logger logger = LoggerFactory.getLogger(VDWSupportService.class);
    
    public VDWSupportService(){
	ApplicationContextProvider.getInstance().getApplicationContext().getAutowireCapableBeanFactory().autowireBean(this);
    }
    
    public List<ATMTransactionEvent> getReletedTransactions(String account){
	List<ATMTransactionEvent> events = new ArrayList<>();
	try{
	    events = atmTransactionRepository.findByAccount(account);
	}catch(Exception ex){
	    logger.error("", ex);
	}
	return events;
    }
}
