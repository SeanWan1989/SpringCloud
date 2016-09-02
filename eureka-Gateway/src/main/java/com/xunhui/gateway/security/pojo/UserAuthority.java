package com.xunhui.gateway.security.pojo;

import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserAuthority implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	@JsonIgnore
	private User user;

	@NotNull
	private String authority;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof UserAuthority))
			return false;

		UserAuthority ua = (UserAuthority) obj;
		return ua.getAuthority() == this.getAuthority() || ua.getAuthority().equals(this.getAuthority());
	}

	@Override
	public int hashCode() {
		return getAuthority() == null ? 0 : getAuthority().hashCode();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ": " + getAuthority();
	}
}
