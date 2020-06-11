package com.cg.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cg.entity.MyUserDetails;
import com.cg.entity.User;
import com.cg.repo.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username)
				.orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
		logger.info(user.toString());
		return new MyUserDetails(user);
	}

}
