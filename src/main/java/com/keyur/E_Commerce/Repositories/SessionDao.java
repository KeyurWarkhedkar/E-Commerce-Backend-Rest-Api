package com.keyur.E_Commerce.Repositories;

import com.keyur.E_Commerce.Entities.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionDao extends JpaRepository<UserSession, Integer> {
    Optional<UserSession> findByToken(String token);
    Optional<UserSession> findByUserId(Integer userId);
    Optional<UserSession> findByUserIdAndUserType(Integer userId, String userType);
}
