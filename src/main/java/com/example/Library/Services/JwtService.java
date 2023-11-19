package com.example.Library.Services;

import com.example.Library.Security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt_secret}")
    private String SECRET_KEY;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
        /*
        subject-y henc username-n e
        */
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /*
        signing key petq e generacnellu ev decode anelu hamar

        signIn key is a secret that is used to digitally sign the JWT. The signIn
        key used to create the signature part of the JWT which is used to verify that
        the sender of the JWT is who it claims to be and ensure that the message wasn't
        changed along the way, so we want to ensure that the same person or the same
        client that is sending this JWT key is the one that claims who to be. The signIn
        key is used in conjunction with the sign in algorithm specified in the JWT header to
        create the signature the specific sign-in algorithm and key size will depend on
        the security requirement of your application and the level of trust you have in the
        signing party
        */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(UserDetailsImpl userDetailsImpl) {
        return generateToken(new HashMap<>(), userDetailsImpl);
    }


    /*
    ays map-y kparunaki claim-er kam extra claim-er voronq vor cankanum enq avelacnel
    */
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetailsImpl userDetailsImpl
    ) {
        return Jwts.
                builder()
                .setClaims(extraClaims)
                .setSubject(userDetailsImpl.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetailsImpl) {
        final String userName = extractUsername(token);
        return (userName.equals(userDetailsImpl.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
