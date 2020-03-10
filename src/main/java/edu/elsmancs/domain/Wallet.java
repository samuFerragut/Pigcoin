package edu.elsmancs.domain;

import java.security.*;
import java.util.*;
import java.util.logging.Level;

public class Wallet {
    private PublicKey address = null;
    private PrivateKey sKey = null;
    private Double total_input = 0d;
    private Double total_output = 0d;
    private Double balance = 0d;
    private List<Transaction> inputTransactions = new ArrayList<>();
    private List<Transaction> outputTransactions = new ArrayList<>();

    void setSK(PrivateKey sKey) {
        this.sKey = sKey;
    }

    PrivateKey getSK() {
        return this.sKey;
    }

    void setAddress(PublicKey aPublic) {
        this.address = aPublic;
    }

    PublicKey getAddress() {
        return this.address;
    }

    void generateKeyPair() {
        KeyPair pair = GenSig.generateKeyPair();
        this.setSK(pair.getPrivate());
        this.setAddress(pair.getPublic());
    }

    void setTotalInput(double total_input) {
        this.total_input = total_input;
    }

    double getTotalInput() {
        return total_input;
    }

    void setTotalOutput(double total_output) {
        this.total_output = total_output;
    }

    double getTotalOutput() {
        return total_output;
    }

    void updateBalance() {
        balance = getTotalInput() - getTotalOutput();
    }

    void loadCoins(BlockChain blockChain) {
        double[] pigcoins = {0d, 0d};
        pigcoins = blockChain.loadWallet(getAddress());
        setTotalInput(pigcoins[0]);
        setTotalOutput(pigcoins[1]);
        updateBalance();
    }

    void setOutputTransactions(List<Transaction> outputTransactions) {
        this.outputTransactions = outputTransactions;
    }

    List<Transaction> getOutputTransactions() {
        return this.outputTransactions;
    }

    void setInputTransactions(List<Transaction> transactions) {
        this.inputTransactions = transactions;
    }

    List<Transaction> getInputTransactions() {
        return this.inputTransactions;
    }

    void loadInputTransactions(BlockChain bChain) {
        setInputTransactions(bChain.loadInputTransactions(getAddress()));
    }

    void loadOutputTransactions(BlockChain bChain) {
        setOutputTransactions(bChain.loadOutputTransactions(getAddress()));
    }

    Map<String, Double> collectCoins(double pigcoins) {
        Map<String, Double> collectedCoins = new LinkedHashMap<String, Double>();

        if (getInputTransactions() == null) {
            return null;
        }

        if (pigcoins > getBalance()) {
            return null;
        }
        Double achievedCoins = 0d;

        Set<String> consumedCoins = new HashSet<String>();
        if (getOutputTransactions() != null) {
            for (Transaction transaction : getOutputTransactions()) {
                consumedCoins.add(transaction.getPrev_hash());
            }
            for (Transaction transaction : getInputTransactions()) {
                if (consumedCoins.contains(transaction.getHash())) {
                    continue;
                }

                if (transaction.getPigCoins() == pigcoins) {
                    collectedCoins.put(transaction.getHash(), transaction.getPigCoins());
                    consumedCoins.add(transaction.getHash());
                    break;
                } else if (transaction.getPigCoins() > pigcoins) {
                    collectedCoins.put(transaction.getHash(), pigcoins);
                    collectedCoins.put("CA_" + transaction.getHash(), transaction.getPigCoins() - pigcoins);
                    consumedCoins.add(transaction.getHash());
                    break;
                } else {
                    collectedCoins.put(transaction.getHash(), transaction.getPigCoins());
                    achievedCoins = transaction.getPigCoins();
                    pigcoins = pigcoins - achievedCoins;
                    consumedCoins.add(transaction.getHash());
                }

            }
            // getInputTransactions().removeAll(consumedCoins);
        }
        return collectedCoins;
    }

    double getBalance() {
        return balance;
    }

    void sendCoins(PublicKey pKey_recipient, double coins, String message, BlockChain blockChain) {
        Map<String, Double> consumedCoins = new LinkedHashMap<String, Double>();

        consumedCoins = collectCoins(coins);

        if (consumedCoins != null) {
            blockChain.processTransactions(getAddress(), pKey_recipient, consumedCoins, message, signTransaction(message));
        }

        this.loadCoins(blockChain);
    }

    byte[] signTransaction(String message) {
        return GenSig.sign(getSK(), message);
    }

    @Override
    public String toString() {
        return "\nWallet = " + getAddress().hashCode() +
                "\nTotal input = " + this.total_input +
                "\nTotal output = " + this.total_output +
                "\nBalance = " + this.balance;
    }
}

