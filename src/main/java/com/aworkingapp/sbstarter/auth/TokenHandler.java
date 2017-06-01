package com.aworkingapp.sbstarter.auth;

import com.aworkingapp.sbstarter.auth.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by chen.liu on 6/3/2015.
 */
public class TokenHandler {
    private static final String HMAC_ALGO = "HmacSHA256";
    private static final String SEPARATOR = ".";
    private static final String SEPARATOR_SPLITTER = "\\.";

    private final Mac hmac;
    private final Mac hmacRefresh;

    public TokenHandler(byte[] secretKey, byte[] refreshSecrectKey) {
        try {
            this.hmac = Mac.getInstance(HMAC_ALGO);
            this.hmacRefresh = Mac.getInstance(HMAC_ALGO);
            this.hmac.init(new SecretKeySpec(secretKey, HMAC_ALGO));
            this.hmacRefresh.init(new SecretKeySpec(refreshSecrectKey, HMAC_ALGO));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new IllegalStateException("failed to initialize HMAC: " + e.getMessage(), e);
        }
    }

    public User parseUserFromToken(String token) {
      final String[] parts = token.split(SEPARATOR_SPLITTER);
      if (parts.length == 2 && parts[0].length() > 0 && parts[1].length() > 0) {
        try {
          final byte[] userBytes = fromBase64(parts[0]);
          final byte[] hash = fromBase64(parts[1]);

          boolean validHash = Arrays.equals(createHmac(userBytes), hash);
          if (validHash) {
            final User user = fromJSON(userBytes);
            if (new Date().getTime() < user.getExpires()) {
              return user;
            }
          }
        } catch (IllegalArgumentException e) {
          //log tempering attempt here
        }
      }
      return null;
    }

    public String createTokenForUser(User user) {
        byte[] userBytes = toJSON(user);
        byte[] hash = createHmac(userBytes);
        final StringBuilder sb = new StringBuilder(170);
        sb.append(toBase64(userBytes));
        sb.append(SEPARATOR);
        sb.append(toBase64(hash));
        return sb.toString();
    }

    public String createRefreshTokenForUser(String username){
        //using current time stamp as salt for now
        String salt = (System.currentTimeMillis()+"").substring(4);
        String tokenString = username+salt;
        byte[] tokenStringBytes =  tokenString.getBytes(Charset.forName("UTF-8"));
        byte[] hash = createHmacRefresh(tokenStringBytes);
        final StringBuilder sb = new StringBuilder();
        sb.append(hash);
        return sb.toString();
    }

    private User fromJSON(final byte[] userBytes) {
        try {
            return new ObjectMapper().readValue(new ByteArrayInputStream(userBytes), User.class);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private byte[] toJSON(User user) {
        try {
            return new ObjectMapper().writeValueAsBytes(user);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    private String toBase64(byte[] content) {
        return DatatypeConverter.printBase64Binary(content);
    }

    private byte[] fromBase64(String content) {
        return DatatypeConverter.parseBase64Binary(content);
    }

    // synchronized to guard internal hmac object
    private synchronized byte[] createHmac(byte[] content) {
        return hmac.doFinal(content);
    }

    private synchronized byte[] createHmacRefresh(byte[] content) {
        return hmacRefresh.doFinal(content);
    }
}
