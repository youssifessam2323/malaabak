package com.joework.Malaabak.models;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.joework.Malaabak.validators.Password;

@Entity(name = "users")
public class User implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank @NotNull
	private String fullName;

	@Email
	private String email;
	
	@Password
	private String password;

	private ROLE role;
	
	
	private boolean isEnabled;
	
	private boolean isAccountExpired;
	
	private boolean isAccountLocked;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return role.getAuthorites();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRole(ROLE role) {
		this.role = role;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public void setAccountExpired(boolean isAccountExpired) {
		this.isAccountExpired = isAccountExpired;
	}

	public void setAccountLocked(boolean isAccountLocked) {
		this.isAccountLocked = isAccountLocked;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !isAccountExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !isAccountLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

}
