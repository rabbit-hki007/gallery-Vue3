package org.africalib.gallery.backend.service;

import io.jsonwebtoken.Claims;

public interface JwtService {

    String getToken(String key, Object value);

    Claims getClaims(String token);

    // 카트정보 요청시 요청한 회원정보가 유효한지를 체크하는 함수
    boolean isValid(String token);
    // 위의 체크에서 이상이 없을시 회원 정보를 가져오는 함수
    int getId(String token);

}
