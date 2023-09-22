package com.whatever.ofi.requestDto;

import com.whatever.ofi.Enum.Gender;
import lombok.Getter;

@Getter
public class CoordinatorInfoRequest {
    private String nickname;

    private String sns_url;

    private String image_url;

    private String content;

    private Gender gender;

    private int height;

    private int weight;

    private int total_like;

    private int request_count;
}
