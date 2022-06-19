package com.joework.Malaabak.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.joework.Malaabak.exceptions.UserNotFoundException;
import com.joework.Malaabak.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	
	@Autowired
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		log.info("trying to find get user {} ...", email);
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("user " + email + "not exist"));
	}

}
