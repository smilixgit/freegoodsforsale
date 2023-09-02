package com.example.yecaoshi.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.Verification;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtUtil {
    /**
     * 定义 token 返回头部
     */
    public static String header;

    /**
     * token 前缀
     */
    public static String tokenPrefix;

    /**
     * 签名密钥
     */
    public static String secret;

    /**
     * 有效期
     */
    public static long expireTime;

    /**
     * 存进客户端的 token 的 key 名
     */
    public static final String USER_LOGIN_TOKEN = "token";

    /**
     * 设置 token 头部
     * @param header token 头部
     */
    public void setHeader(String header) {
        JwtUtil.header = header;
    }

    /**
     * 设置 token 前缀
     * @param tokenPrefix token 前缀
     */
    public void setTokenPrefix(String tokenPrefix) {
        JwtUtil.tokenPrefix = tokenPrefix;
    }

    /**
     * 设置 token 密钥
     * @param secret token 密钥
     */
    public void setSecret(String secret) {
        JwtUtil.secret = secret;
    }

    /**
     * 设置 token 有效时间
     * @param expireTimeInt token 有效时间
     */
    public void setExpireTime(int expireTimeInt) {
        JwtUtil.expireTime = 60*24*30*30;
    }

    /**
     * 创建 TOKEN
     * JWT 构成: header, payload, signature
     * @param sub jwt 所面向的用户，即用户名
     * @return token 值
     */
    public static String createToken(String sub) {
        return tokenPrefix + JWT.create()
                .withSubject(sub)

                .sign(Algorithm.HMAC512(secret));
    }

    /**
     * 验证 token
     * @param token 验证的 token 值
     * @return 用户名
     */
    public static String validateToken(String token) throws Exception {
        try {
            Verification verification = JWT.require(Algorithm.HMAC512(secret));
            JWTVerifier jwtVerifier = verification.build();
            // 去除 token 的前缀
            String noPrefixToken = token.replace(tokenPrefix, "");
            DecodedJWT decodedJwt = jwtVerifier.verify(noPrefixToken);
            if(decodedJwt != null) {
                return decodedJwt.getSubject();
            }
            return "";
        } catch (TokenExpiredException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
            return "";
        }
        return "";
    }

    /**
     * 检查 token 是否需要更新
     * @param token token 值
     * @return 过期时间
     */
    public static boolean isNeedUpdate(String token) throws Exception {
        // 获取 token 过期时间
        Date expiresAt = null;
        try {
            expiresAt = JWT.require(Algorithm.HMAC512(secret))
                    .build()
                    .verify(token.replace(tokenPrefix, ""))
                    .getExpiresAt();
        } catch (TokenExpiredException e){
            return true;
        } catch (Exception e){
            throw new Exception(("token 验证失败"));
        }
        // 需要更新
        return (expiresAt.getTime() - System.currentTimeMillis()) < (expireTime >> 1);
    }
}
