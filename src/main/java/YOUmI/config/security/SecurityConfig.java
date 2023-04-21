package YOUmI.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {

    //@Autowired
    private final UserDetailsService userDetailsService;

    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.error(new BCryptPasswordEncoder().encode("test"));
        log.error(userDetailsService.toString());
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }


    /*
    @EnableWebSecurity 어노테이션이 달려있는 클래스에서 @Bean으로 SecurityFilterChain을 정의하면,
    이 메소드가 반환하는 SecurityFilterChain 객체가 필터 체인에 등록됩니다.
    Spring Security에서 필터 체인은 FilterChainProxy 클래스를 이용해서 생성됩니다.
    FilterChainProxy 클래스는 SecurityFilterChain 객체들을 가지고 있고, 이들을 순차적으로 실행하면서 필터 체인을 구성합니다.
    따라서 SecurityConfig 클래스에서 @Bean으로 정의한 SecurityFilterChain 객체도 FilterChainProxy 클래스가 생성하는 필터 체인 중 하나로 등록되게 됩니다.
    이 SecurityFilterChain 객체는 기본적으로 BasicAuthenticationFilter 다음에 등록되며, 다른 필터 체인과 동일한 방식으로 동작합니다.
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .exceptionHandling().authenticationEntryPoint(userAuthenticationEntryPoint)
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests()
                //.antMatchers("/admin/**").hasRole("ADMIN")
                //.antMatchers("/user/**").hasRole("USER")
//                .antMatchers("/login").permitAll()
//                .anyRequest().authenticated()
//                .anyRequest().hasRole("USER")
                .antMatchers("/MBTI").permitAll()
                .and()
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
//                .formLogin()
//                .defaultSuccessUrl("/MBTI/questions")
//                //.loginPage("/login")
//                .successHandler((request, response, authentication) -> new SimpleUrlAuthenticationSuccessHandler("/").onAuthenticationSuccess(request, response, authentication))
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll()
//                .and()
                .build();
    }


}
