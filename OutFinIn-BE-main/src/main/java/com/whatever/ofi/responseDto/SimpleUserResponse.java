package com.whatever.ofi.responseDto;

import com.whatever.ofi.Enum.Gender;
import com.whatever.ofi.domain.Coordinator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SimpleUserResponse {
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String snsUrl;
    private String imageUrl;
    private String content;
    private int height;
    private int weight;
    private Gender gender;
    private int totalLike;
    private int requestCount;
    private List<String> styles = new ArrayList<>();

    public SimpleUserResponse(Long id, String email, String password, String nickname, String snsUrl, String imageUrl,
                              String content, int height, int weight, Gender gender, int totalLike, int requestCount,
                              List<String> styles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.snsUrl = snsUrl;
        this.imageUrl = imageUrl;
        this.content = content;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.totalLike = totalLike;
        this.requestCount = requestCount;
        this.styles = styles;

    }

    public static SimpleUserResponse of(Coordinator user) {
        return new SimpleUserResponse(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getNickname(),
                user.getSns_url(),
                user.getImage_url(),
                user.getContent(),
                user.getHeight(),
                user.getWeight(),
                user.getGender(),
                user.getTotal_like(),
                user.getRequest_count(),
                user.getStyles()
        );
    }
}