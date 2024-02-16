package com.rajen.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category {

	@Id
	@Column(name = "id")
	private String categoryid;
     @NotBlank
     @Column(length = 60 ,nullable = false)
	private String title ;
     
     @Column(length = 500)
	private String discription;
	private String coverimage;
	
	
	@OneToMany( mappedBy = "category", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	
	private List<Product> product= new ArrayList<>();
}
