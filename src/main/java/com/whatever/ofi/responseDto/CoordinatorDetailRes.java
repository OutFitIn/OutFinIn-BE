package com.whatever.ofi.responseDto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class CoordinatorDetailRes {

    private String nickname;

    private String profile_image;

    private String content;

    private int total_like;

    private int request_count;

    private List<String> styles;

    List<CoordinatorAllBoardRes> boards = new ArrayList<>();
}
