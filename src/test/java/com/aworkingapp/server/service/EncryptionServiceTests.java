package com.aworkingapp.server.service;

import com.aworkingapp.server.Application;
import com.aworkingapp.server.domain.ConfirmEmailToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by chen on 2017-06-29.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class EncryptionServiceTests {

    @Autowired
    EncryptionService encryptionService;

    @Test
    public void testEncryption() throws NoSuchPaddingException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidAlgorithmParameterException {

        String test = "test";
        String encrypted = encryptionService.encrypt(test);

        assertNotNull(encrypted, "not empty");
    }

    @Test
    public void testDecryption() throws NoSuchPaddingException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidAlgorithmParameterException {

        String test = "test";
        String encrypted = encryptionService.encrypt(test);
        String decrypted = encryptionService.decript(encrypted);

        assertEquals("test", decrypted);
    }

    @Test
    public void testJsonString() throws IOException, NoSuchPaddingException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        ObjectMapper objectMapper = new ObjectMapper();

        ConfirmEmailToken confirmEmailToken = new ConfirmEmailToken();
        confirmEmailToken.setEmail("test@test.com");
        confirmEmailToken.setExpireAt(999999);

        String jsonString = objectMapper.writeValueAsString(confirmEmailToken);

        String encrypt = encryptionService.encrypt(jsonString);

        assertNotNull(encrypt);

        String decrypt = encryptionService.decript(encrypt);

        confirmEmailToken = objectMapper.readValue(decrypt, ConfirmEmailToken.class);

        assertEquals("test@test.com", confirmEmailToken.getEmail());
        assertEquals(999999, confirmEmailToken.getExpireAt());
    }
}
