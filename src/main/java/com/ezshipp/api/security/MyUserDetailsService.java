package com.ezshipp.api.security;


import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ezshipp.api.persistence.entity.UserEntity;
import com.ezshipp.api.repository.UserRepository;



@Service
public class MyUserDetailsService implements UserDetailsService {
	
	
	@Autowired
	UserRepository userRepository;

	private Collection<? extends GrantedAuthority> authorities;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserEntity> user = userRepository.findByEmail(username);
		 if (user == null) {
	            throw new UsernameNotFoundException(username);
	        }
	    
		return (UserDetails) build(user.get());
	}
	
	public static  UserDetailsImpl build(UserEntity user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList());

		return new UserDetailsImpl();
	}
	
}
