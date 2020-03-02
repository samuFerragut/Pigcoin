package edu.elsmancs.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.security.KeyPair;


public class WalletTest {

    private Wallet wallet;
    private BlockChain blockChain;

    @Before
    public void setUp() {
        wallet = new Wallet();
        blockChain = new BlockChain();
    }

    @Test
    public void getAddressTest() {
        KeyPair pair = GenSig.generateKeyPair();
        wallet.setAddress(pair.getPublic());
        assertEquals(wallet.getAddress().hashCode(), wallet.getAddress().hashCode());
        System.out.println(wallet.getAddress().hashCode());
    }

    @Test
    public void generateKeyPairTest() {
        wallet.generateKeyPair();
        assertEquals(wallet.getAddress().hashCode(), wallet.getAddress().hashCode());
        System.out.println(wallet.getAddress().hashCode());
        assertEquals(wallet.getSK().hashCode(), wallet.getSK().hashCode());
        System.out.println(wallet.getSK().hashCode());
    }

    @Test
    public void loadCoinsTest() {
        Wallet wallet2 = new Wallet();
        wallet.generateKeyPair();
        wallet2.generateKeyPair();

        Transaction transaction = new Transaction("hash", "0", wallet.getAddress(), wallet.getAddress(), 20, "a flying pig!");
        Transaction transaction2 = new Transaction("hash_1", "1", wallet2.getAddress(), wallet2.getAddress(), 10, "a flying fish!");

        blockChain.addOrigin(transaction);
        blockChain.addOrigin(transaction2);

        wallet2.loadCoins(blockChain);
        wallet.loadCoins(blockChain);

        assertEquals(10, wallet.getTotalInput(), 0);
        assertEquals(20, wallet.getTotalOutput(), 0);
        assertEquals(20, wallet2.getTotalInput(), 0);
        assertEquals(10, wallet2.getTotalOutput(), 0);
    }
}
