package YOUmI.config.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


/*
    JWT 토큰을 다루는 방식에 대해서
    JWT 토큰을 발급 요청할 경우 access token,refresh token 두 가지를 한번에 발급받게 됩니다.
    access token이 만료된 경우 refresh token을 통해 새로운 access token 발급 요청을 할 수 있습니다.
    refresh token이 만료된 경우 새로운 refresh token을 발급받아야 하며, 만료된 토큰을 통한 요청의 경우 요청 처리에 실패한 것으로 취급합니다.
    TODO
    - refresh token을 이용하면 여러개의 access token을 계속 발급받을 수 있을텐데 이건 어떻게 처리할지
*/
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String token;

    public JwtAuthenticationToken(String token, Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.token = token;
    }


    // password를 지칭함
    // secret-key, password는 내부적으로만 조회해 사용하는게 좋을 것 같음
    @Override
    public Object getCredentials() {
        return this.getCredentials();
    }

    // UserDetails 또는 id(email)을 지칭함 + Server application-key 저장하는데 사용
    @Override
    public Object getPrincipal() {
        return this.getPrincipal();
    }

    public String getToken() {
        return this.token;
    }
}
