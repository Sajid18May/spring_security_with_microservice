package com.auth.service;

import com.auth.dto.APIResponse;
import com.auth.dto.UserDto;

public interface UserService {
	
	public APIResponse<String> userRegistration(UserDto dto);
}
