package com.sanght.shapechallenge.repository;

import com.sanght.shapechallenge.domain.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessTokenDAO extends JpaRepository<AccessToken, String> {
}
