package com.rajen.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ManyToAny;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
	
	@Id
	private String id;
	
	
	private String title;
	
	
	@Column(length = 5000)
	private String discription;
	
	private int price;
	
	
	private int discount;
	
	
	
	private int quantity;
	
	
	private Date addeddate;
	
	
	private boolean live;
	
	
	private boolean stock;
	
	private String productimage;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	private Category category;
	
	

}
