package edu.elsmancs.domain;

import java.security.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Override
    public String toString() {
        return "\nWallet = " + getAddress().hashCode() +
                "\nTotal input = " + this.total_input +
                "\nTotal output = " + this.total_output +
                "\nBalance = " + this.balance;
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

    void setBalance(double balance) {
        this.balance = balance;
    }

    void loadCoins(BlockChain blockChain) {
        List[] inOutput = blockChain.loadWallet(getAddress());
        setInputTransactions(inOutput[0]);
        setOutpuTransactions(inOutput[1]);
        setTotalInput(loadInputTransactions(blockChain));
        setTotalOutput(loadOutputTransactions(blockChain));
        setBalance(getTotalInput() - getTotalOutput());
    }

    void setInputTransactions(List<Transaction> inputTransactions) {
        this.inputTransactions = inputTransactions;
    }

    List<Transaction> getInputTransactions() {
        return inputTransactions;
    }

    List<Transaction> getOutputTransactions() {
        return outputTransactions;
    }

    double loadInputTransactions(BlockChain blockChain) {
        double input = 0d;
        blockChain.getBlockChain().stream().filter((transaction) -> (transaction.getPKey_recipient().equals(getAddress())))
                .map((transaction) -> transaction.getPigcoins()).reduce(input, (accumulator, _item) -> accumulator + _item);
        return input;
    }

    double loadOutputTransactions(BlockChain blockChain) {
        double output = 0d;
        blockChain.getBlockChain().stream().filter((transaction) -> (transaction.getPKey_recipient().equals(getAddress())))
                .map((transaction) -> transaction.getPigcoins()).reduce(output, (accumulator, _item) -> accumulator + _item);
        return output;
    }

    void setOutpuTransactions(List<Transaction> outpuTransactions) {
        this.outputTransactions = outpuTransactions;
    }

    void sendCoins(PublicKey pKey_recipient, double coins, String message, BlockChain blockChain) {
        Map<String, Double> consumedCoins = collectCoins(coins);
        byte[] signedTransaction = signTransaction(message);
        blockChain.processTransactions(getAddress(), pKey_recipient, consumedCoins, message, signedTransaction);
    }

    byte[] signTransaction(String message) {
        return GenSig.sign(getSK(), message);
    }

    Map<String, Double> collectCoins(double coins) {
        Map<String, Double> consumedCoins = new HashMap<>();
        return consumedCoins;
    }
}

