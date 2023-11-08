package es.javaschool.springbootosisfinal_task.config.jwt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    // Find RefreshToken by token
    Optional<RefreshToken> findByToken(String token);
}
