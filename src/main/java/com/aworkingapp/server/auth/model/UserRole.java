package com.aworkingapp.server.auth.model;

import com.aworkingapp.server.auth.AuthoritiesConstants;

public enum UserRole {

	//TODO user community_admin role can have a list of communities
	SYSTEM_ADMIN, USER, ADMIN, ANONYMOUS;

	public UserAuthority asAuthorityFor() {
		final UserAuthority authority = new UserAuthority();
		authority.setAuthority("ROLE_" + toString());
		return authority;
	}

	public static UserRole valueOf(final UserAuthority authority) {
		switch (authority.getAuthority()) {
			case AuthoritiesConstants.ADMIN:
				return ADMIN;
			case AuthoritiesConstants.USER:
				return USER;
			case AuthoritiesConstants.ANONYMOUS:
				return ANONYMOUS;
			case AuthoritiesConstants.SYSTEM_ADMIN:
				return SYSTEM_ADMIN;
		}
		throw new IllegalArgumentException("No role defined for authority: " + authority.getAuthority());
	}
}
