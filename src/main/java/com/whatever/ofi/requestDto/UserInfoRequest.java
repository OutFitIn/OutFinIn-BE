package com.whatever.ofi.requestDto;

import com.whatever.ofi.Enum.Gender;
import com.whatever.ofi.Enum.Shape;
import lombok.Getter;

@Getter
public class UserInfoRequest {
    private String nickname;

    private int height;

    private int weight;

    private Gender gender;

    private Shape shape;
}
