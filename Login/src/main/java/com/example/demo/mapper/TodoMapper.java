
package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.TodoEntity;
import com.example.demo.form.TodoForm;

@Mapper
public interface TodoMapper {
	
	int create(TodoForm form);
	
	Integer getUserId(String userName);

	List<TodoEntity> getTodoAll(int userId);

	boolean delete(int userId, int id);

}
