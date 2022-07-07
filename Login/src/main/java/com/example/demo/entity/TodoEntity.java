package com.example.demo.entity;

import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table(value="todo_tasks")
public class TodoEntity {
	private Integer id;
	private String comment;
	private boolean state;
	private Integer userId;
	private String deleteFlg;

}
