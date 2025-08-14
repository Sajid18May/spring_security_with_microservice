   package com.auth.serviceimpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.dto.APIResponse;
import com.auth.dto.UserDto;
import com.auth.entity.AppUser;
import com.auth.repository.UserRepository;
import com.auth.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository repository;
	@Autowired
	PasswordEncoder encoder;

	@Override
	public APIResponse<String> userRegistration(UserDto dto) {
		APIResponse<String> apiResponse = new APIResponse<>();
		if (repository.existsByUsername(dto.getUsername())) {
			apiResponse.setMessage("Username Already Exists");
			apiResponse.setStatus(500);
			apiResponse.setData("Duplicate Username");
			return apiResponse;
		}
		if (repository.existsByEmail(dto.getEmail())) {
			apiResponse.setMessage("Email Already Exists");
			apiResponse.setStatus(500);
			apiResponse.setData("Duplicate Email");
			return apiResponse;
		}
		AppUser user=new AppUser();
		BeanUtils.copyProperties(dto, user);
		user.setPassword(encoder.encode(dto.getPassword()));
		repository.save(user);
		apiResponse.setMessage("Registration Successful");
		apiResponse.setStatus(201);
		apiResponse.setData("Success");
		return apiResponse;

	}

}
