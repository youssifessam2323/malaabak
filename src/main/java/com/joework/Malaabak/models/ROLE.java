package com.joework.Malaabak.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static com.joework.Malaabak.models.PERMISSION.*;
public enum ROLE {

	RENTER(List.of(SOCCER_FIELD_CAN_READ)),
	OWNER(List.of(SOCCER_FIELD_CAN_READ , SOCCER_FIELD_CAN_WRITE));
	
	ROLE(List<PERMISSION> permissions) {
		this.permissions = permissions;
	}
	
	private List<PERMISSION> permissions; 
	
	
	public Set<SimpleGrantedAuthority> getAuthorites() {
		
		Set<SimpleGrantedAuthority> authorities = new HashSet<>(); 
		
		authorities = permissions.stream()
				.map(p -> p.name())
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toSet());
		
		authorities.add(new SimpleGrantedAuthority(this.name()));
		
		return authorities; 
	}
	
}
