package com.whatever.ofi.responseDto;

import com.whatever.ofi.Enum.Gender;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CoordinatorInfoRes {
    private String nickname;

    private String sns_url;

    private String image_url;

    private String content;

    private Gender gender;

    private int height;

    private int weight;

    private List<String> styles;

    @Builder
    public CoordinatorInfoRes(String nickname, String sns_url, String image_url,
                              String content, Gender gender, int height, int weight, List<String> styles) {
        this.nickname = nickname;
        this.sns_url = sns_url;
        this.image_url = image_url;
        this.content = content;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.styles = styles;
    }
}
