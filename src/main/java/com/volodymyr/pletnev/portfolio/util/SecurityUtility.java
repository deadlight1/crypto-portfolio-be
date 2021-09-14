package com.volodymyr.pletnev.portfolio.util;

import com.volodymyr.pletnev.portfolio.models.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtility {

	public static User getSecurityPrincipal() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	public static String currentId(){
		return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
	}
}
