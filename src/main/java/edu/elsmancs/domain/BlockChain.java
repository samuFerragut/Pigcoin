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

    void summarize() {
        blockChain.forEach((Transaction) -> {System.out.println(Transaction.toString());});
    }

    void summarize(int position) {
        System.out.println(blockChain.get(position).toString());
    }
}
