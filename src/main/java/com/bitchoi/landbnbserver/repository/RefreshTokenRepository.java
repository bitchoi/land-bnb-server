package com.bitchoi.landbnbserver.repository;

import com.bitchoi.landbnbserver.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
}
