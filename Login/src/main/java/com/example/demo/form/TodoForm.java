package com.example.demo.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class TodoForm {
	
	@NotBlank
	@Length(max= 100 )
	private String comment;
	private boolean state;
	@Min(1)
	private int userId;
	private String userName;
	private int id;

}
