package com.insart.aci.storm.persistance.repository.custom;

import com.insart.aci.storm.model.TransactionEvent;

/**
 * @author Eugene Pehulja
 * @since Jul 22, 2015 11:28:34 AM
 */
public interface GenericTransactionRepositoryCustom {
    public TransactionEvent save(TransactionEvent genericEvent);
}
