package com.volodymyr.pletnev.portfolio.repository;

import com.volodymyr.pletnev.portfolio.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	Boolean existsByEmail(String email);

	Optional<User> findByEmail(String email);
}
