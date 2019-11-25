package com.sanght.shapechallenge.common.constant;

public enum RoleName {
	 ROLE_ADMIN, ROLE_USER, ROLE_ANONYMOUS;

	public static boolean contains(String name) {
		for (RoleName roleName: RoleName.values()) {
			if (roleName.name().equals(name)) {
				return true;
			}
		}
		return false;
	}
}