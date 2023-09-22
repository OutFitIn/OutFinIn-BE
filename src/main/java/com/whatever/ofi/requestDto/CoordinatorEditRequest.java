package com.whatever.ofi.requestDto;

import com.whatever.ofi.Enum.Gender;
import com.whatever.ofi.Enum.Shape;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CoordinatorEditRequest {
    private Long id;

    private String nickname;

    private int height;

    private int weight;

    private Gender gender;

    private String sns_url;

    private String image_url;

    private String content;

    private List<String> styles = new ArrayList<>();
}
