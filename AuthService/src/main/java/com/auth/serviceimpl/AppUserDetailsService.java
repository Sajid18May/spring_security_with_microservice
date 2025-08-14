package com.auth.serviceimpl;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth.entity.AppUser;
import com.auth.repository.UserRepository;

@Service
public class AppUserDetailsService implements UserDetailsService{
	@Autowired
	UserRepository repository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user=repository.findAppUserByUsername(username);
		return new User(username, user.getPassword(), Collections.singleton(new SimpleGrantedAuthority(user.getRole())));
	}

}
