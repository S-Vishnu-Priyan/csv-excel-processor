package com.training.codingstandards;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SecurityUtilTest {

    @Test
    void hashesIdentifiersWithStableSha256Output() {
        String hash = SecurityUtil.hashIdentifier("1001asha@example.com");

        assertEquals(64, hash.length());
        assertEquals(hash, SecurityUtil.hashIdentifier("1001asha@example.com"));
        assertNotEquals(hash, SecurityUtil.hashIdentifier("1002asha@example.com"));
    }

    @Test
    void generatesUniqueTokens() {
        String first = SecurityUtil.sessionToken();
        String second = SecurityUtil.sessionToken();

        assertEquals(64, first.length());
        assertTrue(first.matches("[0-9a-f]+"));
        assertNotEquals(first, second);
    }
}
