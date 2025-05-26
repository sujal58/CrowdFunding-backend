package com.project.crowdfunding.Services.AuthService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${spring.jwt-secret}")
    private String SECRET_KEY;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }


    public String getTokenFromHeader(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7); //Remove Bearer prefix from token
        }

        return null;
    }

    public boolean isTokenExpired(String token){
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }


    public boolean isTokenValid(String token) {
        String username = extractUsername(token);
        UserDetails user = userDetailsServiceImpl.loadUserByUsername(username);
        final String tokenUsername = extractUsername(token);
        return (tokenUsername.equals(user.getUsername()) && !isTokenExpired(token));
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        final Claims claims = parseAllCLaims(token);
        return claimResolver.apply(claims);
    }

    public String createToken(Map<String, Object> claims, String username){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    public String GenerateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }



    private Claims parseAllCLaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey(){
        return new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }
}

