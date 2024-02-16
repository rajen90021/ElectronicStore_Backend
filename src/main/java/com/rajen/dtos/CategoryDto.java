package com.rajen.dtos;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {



	private String categoryid;

	@NotBlank(message = "title is requird")
	@Size(min = 4,message = "title must be minimum 4 charater ")
	private String title ;
     
  @NotBlank(message = "discription requird")

	private String discription;
	private String coverimage;
}
