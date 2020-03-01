package edu.elsmancs.domain;

import org.junit.Test;
import static org.junit.Assert.*;
import java.security.KeyPair;


public class WalletTest {

    @Test
    public void getAddressTest() {
        Wallet wallet = new Wallet();
        KeyPair pair = GenSig.generateKeyPair();
        wallet.setAddress(pair.getPublic());
        assertEquals(wallet.getAddress().hashCode(), wallet.getAddress().hashCode());
        System.out.println(wallet.getAddress().hashCode());
    }

    @Test
    public void generateKeyPairTest() {
        Wallet wallet = new Wallet();
        wallet.generateKeyPair();
        assertEquals(wallet.getAddress().hashCode(), wallet.getAddress().hashCode());
        System.out.println(wallet.getAddress().hashCode());
        assertEquals(wallet.getSkey.hashCode(), wallet.getSkey.hashCode());
    }
}
