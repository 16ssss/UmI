package YOUmI.util;

import YOUmI.config.security.JwtAuthenticationToken;
import YOUmI.domain.Authentication.model.dto.AuthDto;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityUtil {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.refresh-secret-key:refresh-secret-key}")
    private String refreshSecretKey;

    @Value("${security.jwt.token.expire-time}")
    private Long expireTime;

    @Value("${security.jwt.token.refresh-expire-time}")
    private Long refreshExpireTime;

    @PostConstruct
    private void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        refreshSecretKey = Base64.getEncoder().encodeToString(refreshSecretKey.getBytes());
    }

    public String createToken(AuthDto credential) {

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + 1000 * (expireTime));

        //TODO
        //요기서 credential 검증필요

        return JWT.create()
                .withIssuer(credential.getId())
                .withIssuedAt(new Date())
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secretKey));

    }

    public String createRefreshToken(String id) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + 1000 * (refreshExpireTime));

        return JWT.create()
                .withIssuer(id)
                .withIssuedAt(new Date())
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(refreshSecretKey));
    }


    public Authentication validateToken(String token) throws JWTVerificationException{
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT decoded = verifier.verify(token);

        return new JwtAuthenticationToken(token, decoded.getIssuer(), null, Collections.emptyList());
    }

    public boolean validateRefreshToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm).build();

        try {
            DecodedJWT decoded = verifier.verify(token);
        } catch(JWTVerificationException jwtE) {
            log.error("JWTVerificationException occured",jwtE);
            return false;
        }

        return true;
    }

}
