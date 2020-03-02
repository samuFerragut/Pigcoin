package edu.elsmancs.domain;

import org.junit.Test;
import static org.junit.Assert.*;

public class BlockChainTest {

    @Test
    public void addOriginTest() {
        BlockChain blockChain = new BlockChain();
        Wallet wallet = new Wallet();
        wallet.generateKeyPair();
        Transaction transaction = new Transaction("hash_1", "0", wallet.getAddress(), wallet.getAddress(), 20, "a flying pig!");
        blockChain.addOrigin(transaction);
        assertEquals(1, blockChain.getBlockChain().size());
    }

    @Test
    public void summarizeTest() {
        BlockChain blockChain = new BlockChain();
        Wallet wallet = new Wallet();
        wallet.generateKeyPair();
        Transaction transaction = new Transaction("hash_1", "0", wallet.getAddress(), wallet.getAddress(), 20, "a flying pig!");
        blockChain.addOrigin(transaction);
        blockChain.summarize();
    }

    @Test
    public void summarizePositionTest() {
        BlockChain blockChain = new BlockChain();
        Wallet wallet = new Wallet();
        wallet.generateKeyPair();
        Transaction transaction = new Transaction("hash_1", "0", wallet.getAddress(), wallet.getAddress(), 20, "a flying pig!");
        blockChain.addOrigin(transaction);
        blockChain.summarize(0);
    }
}
