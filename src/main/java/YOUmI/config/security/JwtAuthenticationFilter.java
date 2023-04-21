package YOUmI.config.security;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 클라이언트 요청에서 인증 정보 추출
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
//
        if (StringUtils.isNotBlank(header)) {
            String[] authElements = header.split(" ");

            if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
                // 추출한 정보로 인증 객체 생성
                JwtAuthenticationToken jwtAuthToken = new JwtAuthenticationToken(authElements[1], null, null, null);

                // AuthenticationManager에 인증 객체 전달하여 인증 처리 수행
                return this.getAuthenticationManager().authenticate(jwtAuthToken);
            }
        }

        return null;
    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        this.get
//
//        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
//
//        if (StringUtils.isNotBlank(header)) {
//            String[] authElements = header.split(" ");
//
//            if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
//                try {
//                    SecurityContextHolder.getContext().setAuthentication(SecurityUtil.validateToken(authElements[1]));
//                } catch(JWTVerificationException jwtE) {
//                    SecurityContextHolder.clearContext();
//                    throw jwtE;
//                }
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
}