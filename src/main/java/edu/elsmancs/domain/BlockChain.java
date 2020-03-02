package edu.elsmancs.domain;

import java.util.ArrayList;
import java.util.List;

public class BlockChain {
    private ArrayList<Transaction> blockChain = new ArrayList<Transaction>();

    public BlockChain() {}


    void addOrigin(Transaction transaction) {
        blockChain.add(transaction);
    }


    ArrayList<Transaction> getBlockChain() {
        return this.blockChain;
    }
}
