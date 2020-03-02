package edu.elsmancs.domain;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BlockChain {
    private List<Transaction> blockChain = new ArrayList<>();

    public BlockChain() {}


    void addOrigin(Transaction transaction) {
        blockChain.add(transaction);
    }


    List<Transaction> getBlockChain() {
        return this.blockChain;
    }

    void summarize() {
        blockChain.forEach((Transaction) -> {System.out.println(Transaction.toString());});
    }

    void summarize(int position) {
        System.out.println(blockChain.get(position).toString());
    }

    public List<Transaction> loadInputTransactions(PublicKey address) {
        List<Transaction> inputTransactions = getBlockChain().stream().filter(transaction -> transaction.getPKey_recipient().equals(address))
                .collect(Collectors.toCollection(ArrayList<Transaction>::new));
        return inputTransactions;
    }
}
