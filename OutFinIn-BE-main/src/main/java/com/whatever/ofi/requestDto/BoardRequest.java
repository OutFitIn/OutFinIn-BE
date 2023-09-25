package com.whatever.ofi.requestDto;

import com.whatever.ofi.domain.Board;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BoardRequest {
    private Long coordinator_id;
    private String style;
    private int like_count;
    private String season;
    private String situation;
    private String content;
    private String image_url;
    private String title;

    public Board toEntity() {
        return Board.builder()
                .style(style)
                .situation(situation)
                .content(content)
                .image_url(image_url)
                .like_count(like_count)
                .season(season)
                .title(title)
                .build();
    }
}
