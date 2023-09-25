package com.whatever.ofi.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CoordinatorAllBoardRes {
    private Long board_id;

    private String title;

    private String image_url;
}
