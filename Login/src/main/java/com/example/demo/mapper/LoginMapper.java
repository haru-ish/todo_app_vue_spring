package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.UserEntity;

@Mapper
public interface LoginMapper {

	List<UserEntity> getUserList();
	
	// Find user info from name
	UserEntity findByName(String name);

}
