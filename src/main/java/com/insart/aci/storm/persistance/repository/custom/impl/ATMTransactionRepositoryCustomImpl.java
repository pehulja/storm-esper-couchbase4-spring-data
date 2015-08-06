package com.insart.aci.storm.persistance.repository.custom.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.stereotype.Repository;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.query.Query;
import com.insart.aci.storm.model.ATMTransactionEvent;
import com.insart.aci.storm.persistance.repository.custom.ATMTransactionRepositoryCustom;

/**
 * @author Eugene Pehulja
 * @since Jul 22, 2015 11:30:54 AM
 */
@Repository
public class ATMTransactionRepositoryCustomImpl extends GenericTransactionRepositoryCustomImpl implements ATMTransactionRepositoryCustom {
    @Autowired
    CouchbaseTemplate template;

/*    @Override
    public List<ATMTransactionEvent> findByAccount(String account) {
	Query query = Query.parameterized("$SELECT_ENTITY$ where and account = $1 ", JsonArray.from(account));
	return template.findByN1QL(query, ATMTransactionEvent.class);
    }*/
}
