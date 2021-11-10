package com.trading.chart.application.tunnel;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.trading.chart.domain.user.User;
import com.trading.chart.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * @author SeongRok.Oh
 * @since 2021/11/08
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class UpbitTradeAPIHeader implements TradeAPIHeader {

    private final UserRepository userRepository;

    @Override
    public HttpHeaders getHeaders(String id) {
        User user = userRepository.findById(id).orElseThrow(RuntimeException::new);
        JWTCreator.Builder tokenCreatorBuilder = accessKeyBuild(user.getUpbitAccessKey());
        return signJwtToken(tokenCreatorBuilder, user.getUpbitSecretKey());
    }

    @Override
    public HttpHeaders getHeaders(String id, Object data) {
        User user = userRepository.findById(id).orElseThrow(RuntimeException::new);
        JWTCreator.Builder tokenCreatorBuilder = accessKeyBuild(user.getUpbitAccessKey());
        includeToken(tokenCreatorBuilder, data);
        return signJwtToken(tokenCreatorBuilder, user.getUpbitSecretKey());
    }

    private void includeToken(JWTCreator.Builder builder, Object data) {
        Field[] declaredFields = data.getClass().getDeclaredFields();
        ArrayList<String> queryElements = new ArrayList<>();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                queryElements.add(field.getName() + "=" + field.get(data));
            } catch (IllegalAccessException e) {
                log.info("Reflection Error. {}", e);
            }
        }

        String queryString = String.join("&", queryElements.toArray(new String[0]));

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(queryString.getBytes("UTF-8"));
            String queryHash = String.format("%0128x", new BigInteger(1, md.digest()));
            builder.withClaim("query_hash", queryHash);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            log.info("Reflection Error. {}", e);
        }
    }

    private JWTCreator.Builder accessKeyBuild(final String accessKey) {
        return JWT.create()
                .withClaim("access_key", accessKey)
                .withClaim("nonce", UUID.randomUUID().toString());
    }

    private HttpHeaders signJwtToken(JWTCreator.Builder tokenCreatorBuilder, String secretKey) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String jwtToken = tokenCreatorBuilder
                .withClaim("query_hash_alg", "SHA512")
                .sign(algorithm);

        String authenticationToken = "Bearer " + jwtToken;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", authenticationToken);
        return headers;
    }

}
