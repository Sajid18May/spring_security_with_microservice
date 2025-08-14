package com.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auth.entity.AppUser;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long>{
	
	public boolean existsByUsername(String username);
	public boolean existsByEmail(String email);
	public AppUser findAppUserByUsername(String username);
	public AppUser findAppUserByEmail(String email);
}
