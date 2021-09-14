package com.volodymyr.pletnev.portfolio.repository;

import java.util.Optional;

import com.volodymyr.pletnev.portfolio.models.entity.ERole;
import com.volodymyr.pletnev.portfolio.models.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
	Optional<Role> findByName(ERole name);
}
