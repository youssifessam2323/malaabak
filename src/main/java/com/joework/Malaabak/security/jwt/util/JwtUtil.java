package com.joework.Malaabak.security.jwt.util;





import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUtil {

	private JwtUtil() {}
	
    public static String generateAccessToken(int id, String username) {
        var now = System.currentTimeMillis();
        return Jwts
                   .builder()
                   .setId(String.valueOf(id))
                   .setSubject(String.format("%d,%s", id, username))
                   .setIssuer(JwtConfig.ISSUER)
                   .setIssuedAt(new Date(now))
                   .setExpiration(Date.from(ZonedDateTime.now()
                                                .plusDays(JwtConfig.TOKEN_EXPIRY_DURATION)
                                                .toInstant()))
                   .signWith(JwtConfig.key(), JwtConfig.SIGNATURE_ALGORITHM)
                   .compact();
    }
    
    
	private static Claims getClaims(String token) {
        return Jwts
                   .parserBuilder()
                   .setSigningKey(JwtConfig.key())
                   .build()
                  .parseClaimsJws(token.replace(JwtConfig.TOKEN_PREFIX, ""))
                   .getBody();
    }
	
	 public static boolean validate(String token) {
	        try {
	            getClaims(token);
	            return true;
	        } catch (SecurityException ex) {
	            log.error("Invalid JWT signature - {}", ex.getMessage());
	        } catch (MalformedJwtException ex) {
	            log.error("Invalid JWT token - {}", ex.getMessage());
	        } catch (ExpiredJwtException ex) {
	            log.error("Expired JWT token - {}", ex.getMessage());
	        } catch (UnsupportedJwtException ex) {
	            log.error("Unsupported JWT token - {}", ex.getMessage());
	        } catch (IllegalArgumentException ex) {
	            log.error("JWT claims string is empty - {}", ex.getMessage());
	        }
	        return false;
	    }
    
	   private static class JwtConfig {
	        static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;
	        // It should be kept encoded in an environment variable
	        static final String SECRET =
	            "	";
	        static final String TOKEN_PREFIX = "Bearer ";
	        static final String ISSUER = "joe.io";
	        static final int TOKEN_EXPIRY_DURATION = 7; // In days
	        
	        private JwtConfig() {
	        }
	        
	        static SecretKey key() {
				System.out.println(SECRET.length());
	            return Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET));
	        }
	    }

		public static void main(String[] args) {
			String generateAccessToken = JwtUtil.generateAccessToken(123, "Joe");
			System.out.println("token: " + generateAccessToken);

		}
}
