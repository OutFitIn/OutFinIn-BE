package com.whatever.ofi.responseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardDetailRes {
    private String nickname;

    private String profile_image;

    private String board_image;

    private String style;

    private String season;

    private String situation;

    private String content;

    private String title;

    private String sns_url;

    private int like_count;

    private int request_count;

    private boolean like_status;

    @Builder
    public BoardDetailRes(String nickname, String profile_image, String board_image, String style, String season,
                          String situation, int like_count, int request_count, String content, String title, String sns_url, boolean like_status) {
        this.nickname = nickname;
        this.profile_image = profile_image;
        this.board_image = board_image;
        this.style = style;
        this.season = season;
        this.situation = situation;
        this.like_count = like_count;
        this.request_count = request_count;
        this.content = content;
        this.title = title;
        this.sns_url = sns_url;
        this.like_status = like_status;
    }
}
