package com.aworkingapp.server.auth.service;

import com.aworkingapp.server.auth.TokenHandler;
import com.aworkingapp.server.auth.model.RefreshToken;
import com.aworkingapp.server.auth.model.User;
import com.aworkingapp.server.auth.model.UserAuthentication;
import com.aworkingapp.server.auth.model.UserInfoModel;
import com.aworkingapp.server.rest.AccountResource;
import com.aworkingapp.server.service.EncryptionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by chen.liu on 6/3/2015.
 */
@Service
public class TokenAuthenticationService {
    public static final String BEARER = "bearer ";

    // interval default to 2 hours
    public static final long EXPIRES_INTERVAL = 1000 * 60 * 120;

    private static Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationService.class);

    // TODO make this configurable
    private static final String REFRESH_TOKEN_PATH = "/api/auth/v1/token/refresh";

//    @Autowired
//    AuthTokenManager authTokenManager;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EncryptionService encryptionService;

    private final TokenHandler tokenHandler;

    @Autowired
    public TokenAuthenticationService(@Value("${token.secret.user}") String secret) {
        tokenHandler = new TokenHandler(DatatypeConverter.parseBase64Binary(secret));
    }

    public void addAuthentication(HttpServletResponse response, UserAuthentication authentication) {
        final User user = authentication.getDetails();
        String token;

        //TODO should we do the check here?
        long expiresAt = System.currentTimeMillis() + EXPIRES_INTERVAL;
        user.setExpires(expiresAt);

        token = tokenHandler.createTokenForUser(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        // TODO handle all these exceptions
        String encryptedRefreshToken = "";
        try {
            encryptedRefreshToken = encryptionService.encrypt(refreshToken.getRefreshToken());
        } catch (InvalidAlgorithmParameterException e) {
//            e.printStackTrace();
            LOGGER.error("Error encrypt refresh token: {}", e.getMessage());
        } catch (InvalidKeyException e) {
            LOGGER.error("Error encrypt refresh token: {}", e.getMessage());
//            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            LOGGER.error("Error encrypt refresh token: {}", e.getMessage());
//            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Error encrypt refresh token: {}", e.getMessage());
//            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Error encrypt refresh token: {}", e.getMessage());
//            e.printStackTrace();
        } catch (BadPaddingException e) {
            LOGGER.error("Error encrypt refresh token: {}", e.getMessage());
//            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            LOGGER.error("Error encrypt refresh token: {}", e.getMessage());
//            e.printStackTrace();
        }

        //TODO: set user status and add record

        //add this token to toker manager and remove previous token
//        if(authTokenManager.userExist(user.getUserId())){
//            String oldToken = authTokenManager.getToken(user.getUserId());
//            authTokenManager.removeToken(user.getUserId());
//            //the notification should be old user's notification
//        }
//
//        authTokenManager.addToken(user.getUserId(), token);

        UserInfoModel userInfoModel = user.getUserInfoModel();
        userInfoModel.setExpiresAt(expiresAt);
        userInfoModel.setTokenType(BEARER);
        userInfoModel.setIdToken(token);
        userInfoModel.setRefreshToken(encryptedRefreshToken);

        try {
            String result = objectMapper.writeValueAsString(userInfoModel);

            response.setContentType("application/json");

            PrintWriter writer = response.getWriter();
            writer.write(result);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        final String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        // only accepts bearer token
        if (StringUtils.isBlank(token) || !token.toLowerCase().startsWith(BEARER)){
            return null;
        }

        // extract token content with out 'bearer'
        final String tokenContent = token.substring(BEARER.length());

        if (token != null) {
            final User user = tokenHandler.parseUserFromToken(tokenContent);
            if (user != null) {
                if(request.getServletPath().equalsIgnoreCase(REFRESH_TOKEN_PATH)){
                    // its ok to expire is we want to refresh
                    return new UserAuthentication(user);
                } else if(System.currentTimeMillis() < user.getExpires()) {
                    return new UserAuthentication(user);
                }
                // check user is not expired;
                // token does not exist in token manager
//                if(!authTokenManager.tokenExist(user.getUserId(), tokenContent)){
//                    return null;
//                }
            }
        }
        return null;
    }

    private String getRequestIP(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        return request.getRemoteAddr();
    }
}
