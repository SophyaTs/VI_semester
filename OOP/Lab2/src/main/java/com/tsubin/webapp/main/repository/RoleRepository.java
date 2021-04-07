package com.tsubin.webapp.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tsubin.webapp.main.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{	
	List<Role> findAll();
	
	Optional<Role> findById(long id);
	
	Optional<Role> findByTitle(String title);
}
