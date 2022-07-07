package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import com.example.demo.entity.UserEntity;
import com.example.demo.mapper.LoginMapper;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	private LoginMapper loginMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			System.out.println("★" + username);
			UserEntity userEntity = loginMapper.findByName(username);
			System.out.println(userEntity);
			// Return as org.springframework.security.core.userdetails.User
			return new User(userEntity.getUserName(), PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(userEntity.getUserPassword()), new ArrayList<>());
		} catch (Exception e) {
			throw new UsernameNotFoundException("Not find : " + username);
		}
	}
}
