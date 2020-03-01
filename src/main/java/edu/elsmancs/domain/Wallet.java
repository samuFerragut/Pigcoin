package edu.elsmancs.domain;

import java.security.*;

public class Wallet {
    private PublicKey address = null;
    private PrivateKey sKey = null;
    private Double total_input = 0d;
    private Double total_output = 0d;
    private Double balance = 0d;

    void setSK(PrivateKey aPrivate) {
        this.sKey = aPrivate;
    }

    PrivateKey getSK() {
        return this.sKey = sKey;
    }

    void setAddress(PublicKey aPublic) {
        this.address = aPublic;
    }

    PublicKey getAddress() {
        return this.address = address;
    }
}
