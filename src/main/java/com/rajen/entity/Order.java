package com.rajen.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders") // Specify a different table name
public class Order {
	
	@Id
	private String orderid;
	
	
	private String orderstatus;
	
	
	private String paymentstatus;
	
	
	private int orderamount;
	
	@Column(length = 5000)
	private String billingaddress;
	
	
	private String billingphone;
	
	 private String billingName;
	
	private  Date orderDate;
	
	private Date deliverddate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	  @JoinColumn(name = "user_id")
	private Users users;
	
	
	@OneToMany(mappedBy = "order",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private List<OrderItem> orderitems= new ArrayList<>();
	
	
	

}
