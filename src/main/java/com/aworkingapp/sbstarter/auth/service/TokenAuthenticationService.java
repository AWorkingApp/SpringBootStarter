package com.aworkingapp.sbstarter.auth.service;

import com.aworkingapp.sbstarter.auth.TokenHandler;
import com.aworkingapp.sbstarter.auth.model.User;
import com.aworkingapp.sbstarter.auth.model.UserAuthentication;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by chen.liu on 6/3/2015.
 */
@Service
public class TokenAuthenticationService {
    public static final String AUTH_HEADER_NAME = HttpHeaders.AUTHORIZATION;
    public static final String BEARER = "bearer ";
    public static final long SEVEN_DAYS = 1000 * 60 * 60 * 24 * 7;

    @Autowired
    AuthTokenManager authTokenManager;

    private final TokenHandler tokenHandler;

    @Autowired
    public TokenAuthenticationService(@Value("${token.secret.user}") String secret, @Value("{token.secret.refresh}") String refreshSecret) {
        tokenHandler = new TokenHandler(DatatypeConverter.parseBase64Binary(secret), DatatypeConverter.parseBase64Binary(refreshSecret));
    }

    public void addAuthentication(HttpServletResponse response, UserAuthentication authentication) {
        final User user = authentication.getDetails();
        String token;
        //TODO should we do the check here?
//    if (user.isEnabled())
        user.setExpires(System.currentTimeMillis() + SEVEN_DAYS);

        token = tokenHandler.createTokenForUser(user);

        //TODO: set user status and add record

        //add this token to toker manager and remove previous token
        if(authTokenManager.userExist(user.getUsername())){
            String oldToken = authTokenManager.getToken(user.getUsername());
            authTokenManager.removeToken(user.getUsername());
            //the notification should be old user's notification
        }

        authTokenManager.addToken(user.getUsername(), token);

        String result = "{\"header\":\""+AUTH_HEADER_NAME+"\",\"token\":\""+token+"\",\"type\": \""+BEARER.trim()+"\"}";

        response.setContentType("application/json");
        try {
            PrintWriter writer = response.getWriter();
            writer.write(result);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        final String token = request.getHeader(AUTH_HEADER_NAME);

        // only accepts bearer token
        if (StringUtils.isBlank(token) || !token.toLowerCase().startsWith(BEARER)){
            return null;
        }

        // extract token content with out 'bearer'
        final String tokenContent = token.substring(BEARER.length());

        if (token != null) {
            final User user = tokenHandler.parseUserFromToken(tokenContent);
            if (user != null) {
                // token does not exist in token manager
                if(!authTokenManager.tokenExist(user.getUsername(), tokenContent)){
                    return null;
                }
                return new UserAuthentication(user);
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
