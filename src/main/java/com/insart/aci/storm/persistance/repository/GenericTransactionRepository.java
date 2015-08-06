package com.insart.aci.storm.persistance.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.insart.aci.storm.model.TransactionEvent;
import com.insart.aci.storm.persistance.repository.custom.GenericTransactionRepositoryCustom;

/**
 * @author Eugene Pehulja
 * @since Jul 22, 2015 9:47:19 AM
 */
@NoRepositoryBean
public interface GenericTransactionRepository extends CrudRepository<TransactionEvent, String>, GenericTransactionRepositoryCustom {
    @Override
    public TransactionEvent save(TransactionEvent entity);
}
