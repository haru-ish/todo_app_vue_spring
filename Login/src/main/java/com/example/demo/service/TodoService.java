package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.TodoEntity;
import com.example.demo.form.TodoForm;
import com.example.demo.mapper.TodoMapper;

@Transactional
@Service
public class TodoService {
	
	@Autowired
	private TodoMapper todoMapper;
	
	public int create(TodoForm form) {
		return this.todoMapper.create(form);
	}
	
	public Integer getUserId(String userName) {
		return this.todoMapper.getUserId(userName);
	}
	
	public List<TodoEntity> getTodoAll(int userId) {
		return this.todoMapper.getTodoAll(userId);
	}

	public boolean delete(int userId, int id) {
		return this.todoMapper.delete(userId, id);
	}

}
