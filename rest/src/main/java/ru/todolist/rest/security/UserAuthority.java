package ru.todolist.rest.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * <p>This app is supposed to have users of only one authority level.<p>
 * 
 * @author badger
 */
public class UserAuthority implements GrantedAuthority {
	private static final long serialVersionUID = 1L;

	@Override
	public String getAuthority() {
		return "USER";
	}

}
