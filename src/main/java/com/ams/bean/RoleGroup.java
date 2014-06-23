package com.ams.bean;

import javax.persistence.Column;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Table(name = RoleGroup.TABLE_NAME)
public class RoleGroup extends AmsBaseEntity {
	public static final String PERMISSIONS = "permissions";

	public static final String TABLE_NAME = "RoleGroup";

	@Column(name = "groupName")
	@Expose
	public String groupName;

	@Column(name = PERMISSIONS)
	@Expose
	public String permissions;

	@Column(name = "description")
	@Expose
	public String description;
	
	
	public String[] permissionitems;
	

	public String[] getPermissionitems() {
		return permissionitems;
	}

	public void setPermissionitems(String[] permissionitems) {
		this.permissionitems = permissionitems;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
