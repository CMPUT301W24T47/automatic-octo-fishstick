package com.example.eventapptest2;

import org.junit.Test;
import static org.junit.Assert.*;

public class HashTest {

    @Test
    public void testGetHash() {
        String inputHash = "exampleHash";
        hash hashObject = new hash(inputHash);
        assertEquals(inputHash, hashObject.getHash());
    }

    @Test
    public void testSetHash() {
        String newHash = "newHash";
        hash hashObject = new hash("");
        hashObject.setHash(newHash);
        assertEquals(newHash, hashObject.getHash());
    }
}
