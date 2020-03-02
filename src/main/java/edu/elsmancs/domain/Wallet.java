package edu.elsmancs.domain;

import java.security.*;
import java.util.logging.Level;

public class Wallet {
    private PublicKey address = null;
    private PrivateKey sKey = null;
    private Double total_input = 0d;
    private Double total_output = 0d;
    private Double balance = 0d;

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
        this.setTotalOutput(0);
        this.setTotalInput(0);

        for (Transaction transaction : blockChain.getBlockChain()) {
            if (this.getAddress().equals(transaction.getPKey_recipient())) {
                this.setTotalInput(getTotalInput() + transaction.getPigcoins());
            }else{}

            if (this.getAddress().equals(transaction.getPKey_sender())) {
                this.setTotalOutput(getTotalOutput() + transaction.getPigcoins());
            }else {}
        }
        this.setBalance(getTotalInput() - getTotalOutput());
    }
}
