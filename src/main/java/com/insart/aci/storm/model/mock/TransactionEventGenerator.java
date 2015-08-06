package com.insart.aci.storm.model.mock;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.insart.aci.storm.model.ATMTransactionEvent;

/**
 * @author Eugene Pehulja
 * @since Jul 27, 2015 6:01:19 PM
 */
public class TransactionEventGenerator {
    final Logger logger = LoggerFactory.getLogger(TransactionEventGenerator.class);

    private final static int DEFAULT_UNIQUE_ACCOUNT_NUMBER = 2;

    public static List<ATMTransactionEvent> generateEvents() {
	Random random = new Random();
	final int SIZE = 1 + random.nextInt(20);
	return Stream.generate(TransactionEventGenerator::random).limit(SIZE).collect(Collectors.toList());
    }

    public static ATMTransactionEvent random() {
	Random random = new Random();
	return new ATMTransactionEvent(new Date(), "Account_" + random.nextInt(DEFAULT_UNIQUE_ACCOUNT_NUMBER), random.nextInt(1000), "Address" + random.nextInt(5));
    }
}
