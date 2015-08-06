package com.insart.aci.storm.persistance.repository;

import java.util.List;

import org.springframework.data.couchbase.core.view.Query;

import com.insart.aci.storm.model.ATMTransactionEvent;
import com.insart.aci.storm.persistance.repository.custom.ATMTransactionRepositoryCustom;

/**
 * @author Eugene Pehulja
 * @since Jul 30, 2015 11:22:04 AM
 */
public interface ATMTransactionRepository extends GenericTransactionRepository, ATMTransactionRepositoryCustom {
    @Query("$SELECT_ENTITY$ WHERE account = $1")
    public List<ATMTransactionEvent> findByAccount(String account);
}
