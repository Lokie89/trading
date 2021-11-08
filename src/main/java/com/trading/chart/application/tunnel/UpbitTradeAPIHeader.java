package com.trading.chart.application.tunnel;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.trading.chart.domain.user.User;
import com.trading.chart.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author SeongRok.Oh
 * @since 2021/11/08
 */
@RequiredArgsConstructor
@Component
public class UpbitTradeAPIHeader implements TradeAPIHeader {

    private final UserRepository userRepository;

    @Override
    public HttpHeaders getHeaders(String id) {
        User user = userRepository.findById(id).orElseThrow(RuntimeException::new);
        final String accessKey = user.getUpbitAccessKey();
        JWTCreator.Builder tokenCreatorBuilder = JWT.create()
                .withClaim("access_key", accessKey)
                .withClaim("nonce", UUID.randomUUID().toString());

        final String secretKey = user.getUpbitSecretKey();
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
