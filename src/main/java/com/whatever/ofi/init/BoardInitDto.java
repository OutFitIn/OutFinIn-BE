package com.whatever.ofi.init;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardInitDto {
    private String style;

    private int like_count;

    private String season;

    private String situation;

    private String content;

    private String image_url;

    private String title;
}
