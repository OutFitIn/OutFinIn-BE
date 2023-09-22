package com.whatever.ofi.responseDto;

import lombok.Builder;
import lombok.Getter;
import org.apache.kafka.common.protocol.types.Field;

import java.util.List;

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

    private int like_count;

    private int request_count;

    @Builder
    public BoardDetailRes(String nickname, String profile_image, String board_image, String style,
                          String season, String situation, int like_count, int request_count, String content, String title) {
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
    }
}
