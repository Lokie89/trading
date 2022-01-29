package com.trading.chart.application.tunnel;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.trading.chart.common.ConvertType;
import com.trading.chart.domain.user.UpbitUser;
import com.trading.chart.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
        UpbitUser upbitUser = userRepository.findById(id).orElseThrow(RuntimeException::new);
        JWTCreator.Builder tokenCreatorBuilder = accessKeyBuild(upbitUser.getUpbitAccessKey());
        return signJwtToken(tokenCreatorBuilder, upbitUser.getUpbitSecretKey());
    }

    @Override
    public HttpHeaders getHeaders(String id, Object data) {
        UpbitUser upbitUser = userRepository.findById(id).orElseThrow(RuntimeException::new);
        JWTCreator.Builder tokenCreatorBuilder = accessKeyBuild(upbitUser.getUpbitAccessKey());
        includeToken(tokenCreatorBuilder, data);
        return signJwtToken(tokenCreatorBuilder, upbitUser.getUpbitSecretKey());
    }

    private void includeToken(JWTCreator.Builder builder, Object data) {
        String queryString = ConvertType.ObjectToQueryString(data, "client");
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
