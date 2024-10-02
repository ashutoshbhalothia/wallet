package com.example.demo.repository;

import java.util.Optional;

import com.example.demo.dto.CurrentUserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentSessionRepo extends JpaRepository<CurrentUserSession, Integer>{

	CurrentUserSession findByUuid(String uuid);

	@Query("FROM CurrentUserSession a WHERE a.userId=?1")
	Optional<CurrentUserSession> findByUserId(Integer userId);
	
}
