package com.aworkingapp.server.auth.model;

import com.aworkingapp.server.domain.AbsMongoModel;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Document(collection = "user")
public class User extends AbsMongoModel implements UserDetails {

    @JsonCreator
    public User() {
        this.accountEnabled = true;
        this.emailConfirmed = false;
        this.credentialsExpired = false;
        this.accountLocked = false;
        // generate random uuid when user created
        grantRole(UserRole.USER);
    }

    public User(String userId) {
        this();
        this.userId = userId;
    }

    // user name has to be unique ?
    @Indexed(unique = true)
    @JsonIgnore
    private String username;

    @Indexed(unique = true)
    @JsonIgnore
    private String email;

    @Indexed(unique = true)
    private String userId;

    @JsonIgnore
    private boolean emailConfirmed;

    @JsonIgnore
    private String password;

    @JsonIgnore
    protected boolean accountEnabled;

    @JsonIgnore
    private String newPassword;

    private Set<UserAuthority> authorities;

    public String getUserId() {
        return userId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonProperty
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public boolean isEmailConfirmed() {
        return emailConfirmed;
    }

    public void setEmailConfirmed(boolean emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public String getNewPassword() {
        return newPassword;
    }

    @JsonProperty
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    @JsonIgnore
    public Set<UserAuthority> getAuthorities() {
        return authorities;
    }

    // Use Roles as external API
	@JsonIgnore
    public Set<UserRole> getRoles() {
        Set<UserRole> roles = EnumSet.noneOf(UserRole.class);
        if (authorities != null) {
            for (UserAuthority authority : authorities) {
                roles.add(UserRole.valueOf(authority));
            }
        }
        return roles;
    }

	@JsonIgnore
    public void setRoles(Set<UserRole> roles) {
        for (UserRole role : roles) {
            grantRole(role);
        }
    }

	@JsonIgnore
    public void grantRole(UserRole role) {
        if (authorities == null) {
            authorities = new HashSet<UserAuthority>();
        }
        authorities.add(role.asAuthorityFor());
    }

	@JsonIgnore
    public void revokeRole(UserRole role) {
        if (authorities != null) {
            authorities.remove(role.asAuthorityFor());
        }
    }

    public boolean hasRole(UserRole role) {
        return authorities.contains(role.asAuthorityFor());
    }

    // used for token
    @Transient
    private long expires;

    @JsonIgnore
    private boolean accountLocked;

    @JsonIgnore
    private boolean credentialsExpired;

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return accountEnabled;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

	@Override
    public String toString() {
        return getClass().getSimpleName() + ": " + getUsername();
    }

    @JsonIgnore
    @Transient
    public UserInfoModel getUserInfoModel(){
       UserInfoModel userInfoModel = new UserInfoModel();
       userInfoModel.setExpiresAt(this.expires);

       return userInfoModel;
    }
}

