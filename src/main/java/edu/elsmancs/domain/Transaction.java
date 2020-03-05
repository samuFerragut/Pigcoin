package edu.elsmancs.domain;

import java.security.PublicKey;

public class Transaction {
    private String hash = "";
    private String prev_hash = "";
    private PublicKey pKey_sender = null;
    private PublicKey pKey_recipient = null;
    private double pigcoins = 0;
    private String message = "";

    public Transaction() {}

    public Transaction(String hash, String prev_hash, PublicKey pKey_sender, PublicKey pKey_recipient,
                       double pigcoins, String message) {
        this.hash = hash;
        this.prev_hash = prev_hash;
        this.pKey_sender = pKey_sender;
        this.pKey_recipient = pKey_recipient;
        this.pigcoins = pigcoins;
        this.message = message;
    }

    String getHash() {
        return hash;
    }

    String getPrev_hash() {
        return prev_hash;
    }

    PublicKey getPKey_sender() {
        return pKey_sender;
    }

    PublicKey getPKey_recipient() {
        return pKey_recipient;
    }

    double getPigcoins() {
        return pigcoins;
    }

    String getMessage() {
        return message;
    }

    void setpKey_sender(PublicKey pKey_sender) {
        this.pKey_sender = pKey_sender;
    }

    void setpKey_recipient(PublicKey pKey_recipient) {
        this.pKey_recipient = pKey_recipient;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "\nhash = " + getHash() +
                "\nprev_hash = " + getPrev_hash() +
                "\npKey_sender = " + getPKey_sender().hashCode() +
                "\npKey_recipient = " + getPKey_recipient().hashCode() +
                "\npigcoins = " + getPigcoins() +
                "\nmessage = " + getMessage();
    }
}
