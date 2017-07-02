package com.aworkingapp.server.service;

import com.aworkingapp.server.config.properties.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * Created by chen on 2017-06-29.
 */
@Service
public class EncryptionService {

    @Autowired
    ApplicationProperties applicationProperties;

    private Cipher ecipher;
    private Cipher dcipher;
    private SecretKey key;
    private AlgorithmParameterSpec paramSpec;
    // 8-byte Salt
    byte[] salt = {
            (byte) 0xA6, (byte) 0xB1, (byte) 0xC3, (byte) 0x16,
            (byte) 0x19, (byte) 0x22, (byte) 0xF3, (byte) 0x02
    };

    @PostConstruct
    public void setup() throws NoSuchAlgorithmException, InvalidKeySpecException {

        KeySpec keySpec = new PBEKeySpec(applicationProperties.getEncryptionProperties().getKey().toCharArray(), salt, iterationCount);
        key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
        // Prepare the parameter to the ciphers
        paramSpec = new PBEParameterSpec(salt, iterationCount);

    }

    // Iteration count
    int iterationCount = 12;

    public String encrypt(String str) throws InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {

        //Enc process
        ecipher = Cipher.getInstance(key.getAlgorithm());
        ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
        String charSet = "UTF-8";
        byte[] in = str.getBytes(charSet);
        byte[] out = ecipher.doFinal(in);
        String encStr = new String(Base64.getEncoder().encode(out));
        return encStr;
    }

    public String decript(String str) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        dcipher = Cipher.getInstance(key.getAlgorithm());
        dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
        byte[] enc = Base64.getDecoder().decode(str);
        byte[] utf8 = dcipher.doFinal(enc);
        String charSet = "UTF-8";
        String plainStr = new String(utf8, charSet);
        return plainStr;
    }
}
