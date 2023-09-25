package com.whatever.ofi.requestDto;

import com.whatever.ofi.domain.User;
import lombok.Getter;

@Getter
public class UserRequest {
    private String email;
    private String password;
    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .build();
    }
}
