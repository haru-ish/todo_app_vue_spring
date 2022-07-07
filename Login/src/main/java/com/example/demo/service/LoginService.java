package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.UserEntity;
import com.example.demo.mapper.LoginMapper;

@Transactional
@Service
public class LoginService {
	
	@Autowired
	private LoginMapper loginMapper;

	public List<UserEntity> getUserList() {
		return this.loginMapper.getUserList();
	}

}
