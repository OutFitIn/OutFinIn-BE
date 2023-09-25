package com.whatever.ofi.responseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserMainTotalRes {
    private List<UserMainPageRes> pages;

    private List<Long> user_board_like;

    private List<String> styles;

    @Builder
    public UserMainTotalRes(List<UserMainPageRes> pages,
                            List<Long> user_board_like,
                            List<String> styles) {
        this.pages = pages;
        this.styles = styles;
        this.user_board_like = user_board_like;
    }
}
