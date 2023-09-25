package com.whatever.ofi.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserBoardLikeRes {

    Long board_id;

    String title;

    String image_url;
}
