package es.javaschool.springbootosisfinal_task.config.jwt;

import es.javaschool.springbootosisfinal_task.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
// Service class for managing RefreshToken

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private ClientRepository clientRepository;


    // Method to create a new RefreshToken
    public RefreshToken createTokenRefresh(String email){

        RefreshToken refreshToken = RefreshToken.builder()
               .client(clientRepository.findByEmail(email).get())
               .token(UUID.randomUUID().toString())
               .expiration(Instant.now().plusMillis(600000))
               .build();

      return refreshTokenRepository.save(refreshToken);

    }



    // Find RefreshToken by token
    public Optional<RefreshToken> findByToken (String token){
        return refreshTokenRepository.findByToken(token);
    }



    // Verify if the RefreshToken has expired
    public RefreshToken verifyExpiration (RefreshToken token){
        if (token.getExpiration().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh Token");
        }
        return token;
    }
}
