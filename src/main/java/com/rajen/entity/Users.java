package com.rajen.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Users implements UserDetails {

	
	@Id
	private String userid;
	
	private String name;
	
	@Column(unique = true)
	private String email;
	private String password;
	@Column(length = 1000)
	private String about;
	private String gender;
	
	private String userimage;
	
	@OneToMany(mappedBy = "users",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
	private List<Order> orders= new ArrayList<>();
	
	
	
	 @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
		@JoinTable(name = "use_role",joinColumns = @JoinColumn(name="user_id"),inverseJoinColumns = @JoinColumn(name="role_id"))
		private Set<Role> role= new HashSet<>();
	 
	 
	 @OneToOne(mappedBy = "users",cascade = CascadeType.REMOVE)
	 private Cart cart;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		List<GrantedAuthority> authorities = new ArrayList<>();

	    for (Role role : role) {
	        String roleName = role.getRolename();
	        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roleName);
	        authorities.add(authority);
	    }

	    return authorities;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}
