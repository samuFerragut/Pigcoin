package edu.elsmancs.domain;

import org.junit.Test;

public class TransactionTest {
    @Test
    public void toStringTest() {
        Wallet wallet = new Wallet();
        wallet.generateKeyPair();
        Transaction transaction = new Transaction("hash_1", "0", wallet.getAddress(), wallet.getAddress(), 20, "a flying pig!");
        System.out.println(transaction.toString());
    }
}
