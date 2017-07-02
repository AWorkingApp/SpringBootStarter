package com.aworkingapp.server.auth.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Chen.Liu on 05/10/2015.
 */
@Service
public class AuthTokenManager {

    private final static ConcurrentHashMap<String ,String> AUTH_TOKEN_MAP = new ConcurrentHashMap<>();

    public synchronized boolean tokenExist(String username, String token){
        boolean hasKey = AUTH_TOKEN_MAP.containsKey(username);
        if(hasKey){
           return AUTH_TOKEN_MAP.get(username).equals(token);
        }
        return false;
    }

    public synchronized boolean userExist(String username){
        return AUTH_TOKEN_MAP.containsKey(username);
    }

    public synchronized boolean addToken(String username, String token){
       try{
           AUTH_TOKEN_MAP.put(username, token);
           return true;
       }
       catch(Exception e){
           return false;
       }
    }

    public synchronized void removeToken(String username){
        AUTH_TOKEN_MAP.remove(username);
    }

	public synchronized String getToken(String username){
		try{
			return AUTH_TOKEN_MAP.get(username);
		}
		catch (Exception e){
			return "";
		}
	}
}
