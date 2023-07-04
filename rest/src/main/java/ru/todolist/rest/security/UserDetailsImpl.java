package ru.todolist.rest.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ru.todolist.rest.model.User;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;
	private User user;
	private List<GrantedAuthority> authorities;
	
	public UserDetailsImpl(User user) {
		this.user = user;
		authorities = new ArrayList<GrantedAuthority>();
		
		authorities.add(new UserAuthority());
	}
	
	public int getId() {
		return user.getIduser();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getLogin();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
