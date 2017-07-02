package com.aworkingapp.server.rest;

import com.aworkingapp.server.auth.TokenHandler;
import com.aworkingapp.server.auth.common.Constants;
import com.aworkingapp.server.auth.model.User;
import com.aworkingapp.server.auth.service.RefreshTokenService;
import com.aworkingapp.server.auth.service.TokenAuthenticationService;
import com.aworkingapp.server.auth.service.UserRequestInfoService;
import com.aworkingapp.server.rest.model.IdTokenModel;
import com.aworkingapp.server.rest.model.VerifyTokenModel;
import com.aworkingapp.server.service.AccountService;
import com.aworkingapp.server.service.EncryptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chen on 2017-06-29.
 */
@RestController
@RequestMapping("/api/auth/v1/token")
public class TokenResource {

    private static Logger LOGGER = LoggerFactory.getLogger(TokenResource.class);

    @Autowired
    UserRequestInfoService userRequestInfoService;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    AccountService accountService;

    @Autowired
    TokenHandler tokenHandler;

    @Autowired
    EncryptionService encryptionService;

    @GetMapping("/verify")
    public ResponseEntity<VerifyTokenModel> verifyIdToken(){

        VerifyTokenModel verifyTokenModel = new VerifyTokenModel();
        verifyTokenModel.setUserId(userRequestInfoService.getRequestUserId());
        verifyTokenModel.setSite(Constants.SITE_AWORKINGAPP);

        return ResponseEntity.ok(verifyTokenModel);
    }

    @GetMapping("/refresh")
    public ResponseEntity<IdTokenModel> refreshIdToken(
            @RequestParam(value = "token")String refreshToken
    ) {
        String userId = userRequestInfoService.getRequestUserId();

        try {
            // decrypt the token first
            String tokenString = encryptionService.decript(refreshToken);

            if (refreshTokenService.validRefreshToken(tokenString, userId)) {
                User user = accountService.getUserByUserId(userId);
                long expiresAt = System.currentTimeMillis() + TokenAuthenticationService.EXPIRES_INTERVAL;
                user.setExpires(expiresAt);
                String newToken = tokenHandler.createTokenForUser(user);
                IdTokenModel idTokenModel = new IdTokenModel();
                idTokenModel.setIdToken(newToken);
                idTokenModel.setExpiresAt(expiresAt);

                return ResponseEntity.ok(idTokenModel);
            }
        }
        catch(Exception e){
            LOGGER.error("error while refresh token: {}", e.getMessage());
        }

        // error happened
        return ResponseEntity.badRequest().build();
    }
}
