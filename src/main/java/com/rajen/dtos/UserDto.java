package com.rajen.dtos;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.rajen.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDto {

	
	private String userid;
	
	@Size(min = 3 ,max = 20 ,message = "invalid name !!")
	private String name;
	
	
	
//	@Email(message = "invalid message ")
	@NotBlank( message = "emial is requied ")
	@Column(unique = true)
	
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",message = "invalid  email ")
	private String email;
	
	
	
	@NotBlank(message = "password is required ")
	private String password;
	
	
	
	@Column(length = 1000)
	@NotBlank(message = "write something about yourself")
	private String about;
	
	
	@Size(min = 4,max = 6 ,message = "invalid gender ")
	private String gender;
	
	private String userimage;
	
	private Set<Role> role= new HashSet<>();
}
