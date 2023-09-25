package com.whatever.ofi.requestDto;

import com.whatever.ofi.Enum.Gender;
import com.whatever.ofi.domain.Coordinator;
import lombok.Getter;

@Getter
public class CoordinatorRequest {
    private String email;

    private String password;

    private String nickname;

    private String sns_url;

    private String image_url;

    private String content;

    private Gender gender;

    private int height;

    private int weight;

    private int total_like;

    private int request_count;

    public Coordinator toEntity() {
        return Coordinator.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .sns_url(sns_url)
                .image_url(image_url)
                .content(content)
                .gender(gender)
                .height(height)
                .weight(weight)
                .total_like(total_like)
                .request_count(request_count)
                .build();
    }
}
