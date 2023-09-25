package com.whatever.ofi.responseDto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class UserLikeTotalRes {
    private List<UserBoardLikeRes> boards;

    private List<Long> user_board_like;

    @Builder
    public UserLikeTotalRes(List<UserBoardLikeRes> boards, List<Long> user_board_like) {
        this.boards = boards;
        this.user_board_like = user_board_like;
    }
}
