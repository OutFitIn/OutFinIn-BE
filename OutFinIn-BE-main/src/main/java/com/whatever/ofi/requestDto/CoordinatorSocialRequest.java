package com.whatever.ofi.requestDto;

import com.whatever.ofi.domain.Coordinator;
import com.whatever.ofi.domain.CoordinatorSocialLogin;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoordinatorSocialRequest {
    private Coordinator coordinator;
    private String kakaoid;
    private String naverid;
    private String googleid;

    public CoordinatorSocialLogin toEntity() {
        return CoordinatorSocialLogin.builder()
                .coordinator(coordinator)
                .kakaoid(kakaoid)
                .naverid(naverid)
                .googleid(googleid)
                .build();
    }
}
