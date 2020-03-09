package edu.elsmancs.domain;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    List<Transaction> loadInputTransactions(PublicKey address) {
        List<Transaction> inputTransactions = getBlockChain().stream().filter(transaction -> transaction.getPKey_recipient().equals(address))
                .collect(Collectors.toCollection(ArrayList::new));
        return inputTransactions;
    }

    List<Transaction> loadOutputTransactions(PublicKey address) {
        List<Transaction> outputTransactions = getBlockChain().stream().filter(transaction -> transaction.getPKey_sender().equals(address))
                .collect(Collectors.toCollection(ArrayList::new));
        return outputTransactions;
    }

    double[] loadWallet(PublicKey address) {

        double pigcoinIn = 0d;
        double pigcoinOut = 0d;

        for (Transaction transaction : getBlockChain()) {

            if (address.equals(transaction.getPKey_recipient())) {
                pigcoinIn = pigcoinIn + transaction.getPigcoins();
            }

            if (address.equals(transaction.getPKey_sender())) {
                pigcoinOut = pigcoinOut + transaction.getPigcoins();
            }
        }

        double[] pigcoins = {pigcoinIn, pigcoinOut};

        return pigcoins;
    }

    void processTransactions(PublicKey address, PublicKey pKey_recipient, Map<String, Double> consumedCoins, String message, byte[] signedTransaction) {
        isSignatureValid(address, message, signedTransaction);
        isConsumedCoinsValid(consumedCoins);
        createTransaction(address, pKey_recipient, consumedCoins, message, signedTransaction);
    }

    private void createTransaction(PublicKey pKey_sender, PublicKey pKey_recipent, Map<String, Double> consumedCoins, String message, byte[] signedTransaction) {
        Transaction transaction = new Transaction();
        transaction.setpKey_sender(pKey_sender);
        transaction.setpKey_recipient(pKey_recipent);
        transaction.setMessage(message);
        blockChain.add(transaction);
    }

    private void isConsumedCoinsValid(Map<String, Double> consumedCoins) {

    }

    private boolean isSignatureValid(PublicKey address, String message, byte[] signedTransaction) {
        return GenSig.verify(address, message, signedTransaction);
    }
}
