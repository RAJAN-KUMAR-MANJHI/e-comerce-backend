package in.com.furniturebackend.security;



import java.util.Date;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	private final String SECRET = "furniture-secrete-key-1234567890";
	
	//--Token generate --//
	public String generateToken(String mobileNumber, String role) {
		return Jwts.builder().setSubject(mobileNumber).claim("role",role)
				.setIssuedAt(new java.util.Date())
				.setExpiration(new Date (System.currentTimeMillis()+86400000))  // 1 day //
				.signWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .compact();
    }

    // Token validate
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    // Get mobile number from token
    public String getMobileFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // Get role from token
    public String getRoleFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return (String) claims.get("role");
    }
	}


