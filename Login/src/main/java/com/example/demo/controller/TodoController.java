package com.example.demo.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.TodoEntity;
import com.example.demo.form.TodoForm;
import com.example.demo.service.TodoService;

@CrossOrigin("http://localhost:8082")
@RestController
public class TodoController {
	
	@Autowired
	private TodoService ts;
	// Add new task
	@PostMapping("/api/create")
	public int create(@Validated TodoForm form, BindingResult result) {
		System.out.println("I wanna take care of my lover");
		System.out.println(form);
		// Check for validations
		if (result.hasErrors()) {
			System.out.println("Sorry, There is some problem...");
		    return 1;
		  }
		System.out.println("I'm here");
		return ts.create(form);
	}
	// Delete a task
	@PostMapping("/api/delete")
	public boolean delete(int userId, int id) {
		return ts.delete(userId, id);
	}
	
	// Get userId
	@PostMapping("/api/getUserId")
	public Integer getUserId(@RequestParam String userName) {
		System.out.println("username is " + userName);
		return ts.getUserId(userName);
	}
	
	// Get TodoList
	@GetMapping("/api/getTodoAll")
	public List<TodoEntity> getTodoAll(@RequestParam int userId) {
		return ts.getTodoAll(userId);
	}
	


}
