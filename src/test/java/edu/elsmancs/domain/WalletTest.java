package edu.elsmancs.domain;

import org.junit.Test;
import static org.junit.Assert.*;
import java.security.KeyPair;


public class WalletTest {

    @Test
    public void setSKTest() {
        Wallet wallet = new Wallet();
        assertEquals(wallet.getAddress(), wallet.getAddress());
        System.out.println(wallet.getAddress());
    }
}
