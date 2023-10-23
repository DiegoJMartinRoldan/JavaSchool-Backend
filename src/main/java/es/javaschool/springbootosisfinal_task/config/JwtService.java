package es.javaschool.springbootosisfinal_task.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    //Generate and validate tokens

    public static final String SECRETKEY = "+FrZMF1S7poRxYdI6GEekdlVFoKTNndf5t9kH3fcn+YVVTbjkbdsf6epfme7U8nR";


    //Generate a new token
    public String generateTokenMethod(String name){
        Map<String, Object> claims = new HashMap<>();
        return createTokenMethod(claims,name);
    }

    //Create a token with its predefined parameters
    private String createTokenMethod(Map<String, Object> response, String name) {
        return Jwts.builder()
                .claims(response)
                .subject(name).issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*30))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //Extract the name
    public String extractClientName (String token){
        return extractClaim(token, Claims::getSubject);
    }

    //Extract token expriration
    public Date extractExpiration (String token){
        return extractClaim(token,Claims::getExpiration);
    }


    //Extract Claims
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //Return all claims
    public Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .setSigningKey(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    //Check if the token has expired
    private Boolean isTokenExpired(String token){
        return  extractExpiration(token).before(new Date());
    }


    //Validate the token based on the extracted name
    public Boolean validateToken(String token, UserDetails userDetails){
        final String name = extractClientName(token);
        return (name.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    //Sign Secret key
    private Key getKey() {

        byte[] keyInBytes = Decoders.BASE64.decode(SECRETKEY);
        return Keys.hmacShaKeyFor(keyInBytes);
    }


}
