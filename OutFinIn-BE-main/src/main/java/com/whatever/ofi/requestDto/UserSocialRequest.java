package com.whatever.ofi.requestDto;

import com.whatever.ofi.domain.User;
import com.whatever.ofi.domain.UserSocialLogin;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSocialRequest {
    private User user;
    private String kakaoid;
    private String naverid;
    private String googleid;

    public UserSocialLogin toEntity() {
        return UserSocialLogin.builder()
                .user(user)
                .kakaoid(kakaoid)
                .naverid(naverid)
                .googleid(googleid)
                .build();
    }
}
