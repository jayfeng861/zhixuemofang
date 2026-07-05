package com.zhixue.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT工具类
 * <p>
 * 提供JWT Token的生成、解析和验证功能。
 * Token有效期默认为24小时，包含用户ID和用户名信息。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Component
public class JwtConfig {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.header}")
    private String header;

    @Value("${jwt.prefix}")
    private String prefix;

    /**
     * 生成用户JWT Token
     *
     * @param userId   用户ID
     * @param username 用户名
     * @return 生成的JWT Token字符串
     */
    public String generateToken(Long userId, String username) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("username", username)
                .claim("type", "user")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    /**
     * 生成管理员JWT Token
     *
     * @param adminId  管理员ID
     * @param username 用户名
     * @return 生成的JWT Token字符串
     */
    public String generateAdminToken(Long adminId, String username) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .subject(String.valueOf(adminId))
                .claim("username", username)
                .claim("type", "admin")
                .claim("adminId", adminId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    /**
     * 从JWT Token中解析用户ID
     *
     * @param token JWT Token字符串
     * @return 用户ID
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = parseToken(token);
        return Long.parseLong(claims.getSubject());
    }

    /**
     * 从JWT Token中解析管理员ID
     *
     * @param token JWT Token字符串
     * @return 管理员ID
     */
    public Long getAdminIdFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.get("adminId", Long.class);
    }

    /**
     * 从JWT Token中解析用户名
     *
     * @param token JWT Token字符串
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.get("username", String.class);
    }

    /**
     * 从JWT Token中解析token类型
     *
     * @param token JWT Token字符串
     * @return token类型：user/admin
     */
    public String getTokenType(String token) {
        Claims claims = parseToken(token);
        return claims.get("type", String.class);
    }

    /**
     * 验证是否为管理员Token
     *
     * @param token JWT Token字符串
     * @return 如果是管理员Token返回true，否则返回false
     */
    public boolean isAdminToken(String token) {
        try {
            String type = getTokenType(token);
            return "admin".equals(type);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 验证JWT Token是否有效
     *
     * @param token JWT Token字符串
     * @return 如果Token有效返回true，否则返回false
     */
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从HTTP请求中提取JWT Token
     *
     * @param request HTTP请求对象
     * @return 提取到的Token字符串，如果不存在则返回null
     */
    public String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(header);
        if (bearerToken != null && bearerToken.startsWith(prefix)) {
            return bearerToken.substring(prefix.length());
        }
        return null;
    }

    /**
     * 解析JWT Token为Claims对象
     *
     * @param token JWT Token字符串
     * @return 解析后的Claims对象
     * @throws io.jsonwebtoken.JwtException 如果Token无效或过期
     */
    private Claims parseToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
