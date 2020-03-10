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
        List<Transaction> inputTransactions = getBlockChain().stream()
                .filter(transaction -> transaction.getPKey_recipient().equals(address))
                .collect(Collectors.toCollection(ArrayList<Transaction>::new));

        return inputTransactions;
    }

    List<Transaction> loadOutputTransactions(PublicKey address) {

        List<Transaction> outputTransactions = getBlockChain().stream()
                .filter(transaction -> transaction.getPKey_sender().equals(address))
                .collect(Collectors.toCollection(ArrayList<Transaction>::new));

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
    public boolean isSignatureValid(PublicKey pKey, String message, byte[] signedTransaction) {
        return GenSig.verify(pKey, message, signedTransaction);
    }

    public boolean isConsumedCoinValid(Map<String, Double> consumedCoins) {
        for (String hash : consumedCoins.keySet()) {
            for (Transaction transaction : blockChain) {
                if (hash.equals(transaction.getPrev_hash())) {
                    return false;
                }
            }
        }
        return true;
    }
    private void createTransaction(PublicKey pKey_sender, PublicKey pKey_recipient, Map<String, Double> consumedCoins,
                                  String message, byte[] signedTransaction) {

        PublicKey address_recipient = pKey_recipient;
        Integer lastBlock = 0;

        for (String prev_hash : consumedCoins.keySet()) {

            if (prev_hash.startsWith("CA_")) {
                pKey_recipient = pKey_sender;
            }

            lastBlock = blockChain.size() + 1;
            Transaction transaction = new Transaction("hash_" + lastBlock.toString(), prev_hash, pKey_sender,
                    pKey_recipient, consumedCoins.get(prev_hash), message);
            getBlockChain().add(transaction);

            pKey_recipient = address_recipient;
        }
    }

    public void processTransactions(PublicKey pKey_sender, PublicKey pKey_recipient, Map<String, Double> consumedCoins,
                                    String message, byte[] signedTransaction) {

        if (isSignatureValid(pKey_sender, message, signedTransaction) && isConsumedCoinValid(consumedCoins)) {
            // crear las nuevas transacciones y añadirlas al blockchain
            createTransaction(pKey_sender, pKey_recipient, consumedCoins, message, signedTransaction);
        }

    }
}
