package com.microservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.microservice.dto.User;

@FeignClient(name = "AUTHSERVICE")
public interface AuthFeignClient {
	 @GetMapping("auth/v1/getUser")
	 User getUserByUsername(@RequestParam("username") String username, @RequestHeader("Authorization") String token);
}
