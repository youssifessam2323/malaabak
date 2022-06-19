package com.joework.Malaabak.models;

public enum PERMISSION {
	SOCCER_FIELD_CAN_READ("soccer-field:read"),
	SOCCER_FIELD_CAN_WRITE("soccer-field:write"),;

	PERMISSION(String permission) {
		this.permission = permission;
	}
	
	private String permission;
	
	
	public String getPermission() {
		return permission;
	}
}
