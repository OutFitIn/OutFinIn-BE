package com.whatever.ofi.requestDto;

import com.whatever.ofi.Enum.Gender;
import com.whatever.ofi.domain.Coordinator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CoordinatorProfileRequest {

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

    private List<String> styles = new ArrayList<>();

    public Coordinator toEntity() {
        return Coordinator.builder()
                .styles(styles)
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
