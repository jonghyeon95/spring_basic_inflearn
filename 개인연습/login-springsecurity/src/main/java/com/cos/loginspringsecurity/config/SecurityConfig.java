package com.cos.loginspringsecurity.config;

import com.cos.loginspringsecurity.config.userdetail.CustomUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailService customUserDetailService;

//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable() //기본 login 페이지 사용안함
                .csrf().disable()   // csrf 사용안함 => Rest API 에서만 안하는듯?

//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //JWT사용하니깐 세션 사용
//                .and()

                .authorizeRequests()    //다음 리퀘스트에 대한 사용권한 체크
                .antMatchers("/", "/auth/**", "/js/**", "/css/**", "image/**", "/add","/login", "/login/**","/home").permitAll()  //누구나 접근가능
                .anyRequest().authenticated()   //그외 나머지 요청은 모두 인증필요
                .and()

                .formLogin()
                .loginProcessingUrl("/login")  //로그인 페이지 URL
                .defaultSuccessUrl("/home")  //로그인 성공시 이동할 페이지
                .failureUrl("/login/fail") //로그인 실패시 URL
                .usernameParameter("user_id")   //username으로 안하고 변경
                .and()

                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))   //로그아웃 페이지 URL
                .logoutSuccessUrl("/home")
                .invalidateHttpSession(true)    //로그아웃 시 세션 제거
                .and()


        ////JWT인증
//                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
//                .and()
//                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(new BCryptPasswordEncoder());

    }




}
