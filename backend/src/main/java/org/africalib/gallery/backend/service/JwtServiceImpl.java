package org.africalib.gallery.backend.service;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service("jwtService")
public class JwtServiceImpl implements JwtService{

    // 키의 길이는 size >= 256 bits 넘어야 한다
    private String secretKey ="badboy5604!@afdsklfjlkdfsadfdsafsadfsadfsadfsadfsdafsadfasjflkjdsaljfldsaj";
    @Override
    public String getToken(String key, Object value) {

        //유효시간 설정
        Date expTime = new Date();
        expTime.setTime(expTime.getTime() + 1000 * 60 * 5);
        byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
        Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");

        Map<String, Object> map = new HashMap<>();
        map.put(key, value);

        JwtBuilder builder = Jwts.builder().setHeader(headerMap)
                .setClaims(map)
                .setExpiration(expTime)
                .signWith(signKey, SignatureAlgorithm.HS256);

        return builder.compact();
    }

    @Override
    public Claims getClaims(String token) {
     if (token != null && !"".equals(token)) {
            try {
                byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
                Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());
//                Claims claims = Jwts.parserBuilder().setSigningKey(signKey).build().parseClaimsJws(token).getBody();
//                return claims;
                return Jwts.parserBuilder().setSigningKey(signKey).build().parseClaimsJws(token).getBody();
            } catch (ExpiredJwtException e) {
                // 만료됨
            } catch (JwtException e) {
                // 유효하지 않음
            }
        }
        return null;
    }

    // 카트 정보를 가져올때 유효한 회원인지 확인하는 함수
    @Override
    public boolean isValid(String token) {
        // 토큰이 이상이 없다면 true를 리턴함
        return this.getClaims(token) != null;
    }
    // 위에서 이상이 없을때 회원 정보를 가져오는 함수
    @Override
    public int getId(String token) {
        Claims claims = this.getClaims(token);

        if (claims != null) {
            // claims가 null 아니면 멤버의 id를 return
            return Integer.parseInt(claims.get("id").toString());
        }
        // claims가 null이면 0을 return
        return 0;
    }

}
