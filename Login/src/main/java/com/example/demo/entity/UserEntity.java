package com.example.demo.entity;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table(value="login_user")
public class UserEntity {
	
	@Column(value="user_id")
	private Integer userId;
	@Column(value="user_name")
	private String userName;
	@Column(value="user_password")
	private String userPassword;
}
