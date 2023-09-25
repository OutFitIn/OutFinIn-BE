package com.whatever.ofi.responseDto;

import com.whatever.ofi.Enum.Gender;
import com.whatever.ofi.Enum.Shape;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class UserInfoRes {
    private String nickname;

    private int height;

    private int weight;

    private Gender gender;

    private Shape shape;

    private List<String> styles;

    @Builder
    public UserInfoRes(String nickname, int height, int weight,
                       Gender gender, Shape shape, List<String> styles) {
        this.nickname = nickname;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.shape = shape;
        this.styles = styles;
    }
}
