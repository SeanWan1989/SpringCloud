package com.xunhui.gateway.security.pojo;

public enum UserRole {
	USER, ADMIN;

	public UserAuthority asAuthorityFor(final User user) {
		final UserAuthority authority = new UserAuthority();
		authority.setAuthority("ROLE_" + toString());
		authority.setUser(user);
		return authority;
	}

	public static UserRole valueOf(final UserAuthority authority) {
		String role = authority.getAuthority();
		if(role.equals("ROLE_USER")){
			return USER;
		}else if(role.equals("ROLE_ADMIN")){
			return ADMIN;
		}else{
			throw new IllegalArgumentException("No role defined for authority: " + role);
		}
	}
}
