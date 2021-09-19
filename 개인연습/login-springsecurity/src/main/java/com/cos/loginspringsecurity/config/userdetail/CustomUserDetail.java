package com.cos.loginspringsecurity.config.userdetail;

import com.cos.loginspringsecurity.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Getter
public class CustomUserDetail implements UserDetails {

    private User user;

    public CustomUserDetail(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUser_id();
    }

    @Override
    public boolean isAccountNonExpired() {  //계정 만료되면 false
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {   //계정 잠겨있으면 false
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {  //비밀번호 만료되면 false
        return true;
    }

    @Override
    public boolean isEnabled() {    //계정 활성화면 true
        return true;
    }
}
