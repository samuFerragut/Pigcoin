package edu.elsmancs.domain;

import org.junit.Test;
import static org.junit.Assert.*;
import java.security.KeyPair;


public class WalletTest {

    @Test
    public void setSKTest() {
        Wallet wallet = new Wallet();
        KeyPair pair = GenSig.generateKeyPair();
        wallet.setAddress(pair.getPublic());
        assertEquals(wallet.getAddress().hashCode(), wallet.getAddress().hashCode());
        System.out.println(wallet.getAddress().hashCode());
    }
}
