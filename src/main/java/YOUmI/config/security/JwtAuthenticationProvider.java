package YOUmI.config.security;

import YOUmI.util.SecurityUtil;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final SecurityUtil securityUtil;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken jwtAuthToken = authentication instanceof JwtAuthenticationToken ? (JwtAuthenticationToken)authentication : null;
        if(jwtAuthToken == null){
            return null;
        }
        try {
            return securityUtil.validateToken(jwtAuthToken.getToken());
        } catch(TokenExpiredException expiredException){
            log.error("TokenExpiredException occured ",expiredException);
            throw new BadCredentialsException("token is expired", expiredException);
        } catch(JWTVerificationException e) {
            log.error("JWTVerificationException occured ",e);
            throw new AuthenticationServiceException("JWTVerificationException occured", e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
