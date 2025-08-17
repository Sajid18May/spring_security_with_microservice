package com.auth.service;

import com.auth.dto.APIResponse;
import com.auth.dto.UserDto;
import com.auth.entity.AppUser;

public interface UserService {
	
	public APIResponse<String> userRegistration(UserDto dto);
	public AppUser getUserByUsername(String username);
}
