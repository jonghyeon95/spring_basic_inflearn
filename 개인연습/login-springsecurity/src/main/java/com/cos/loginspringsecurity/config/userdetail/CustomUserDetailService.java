package com.cos.loginspringsecurity.config.userdetail;

import com.cos.loginspringsecurity.domain.User;
import com.cos.loginspringsecurity.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String user_id) throws UsernameNotFoundException {
        log.info("userdetails 시작");

        User user = userMapper.getUserByUserId(user_id);
        if (user == null) {
            throw new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다 " + user_id);
        }
        UserDetails customUserDetail = new CustomUserDetail(user);
        return customUserDetail;
    }
}
