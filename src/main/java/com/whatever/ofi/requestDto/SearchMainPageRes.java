package com.whatever.ofi.requestDto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchMainPageRes {

    private Long board_id;

    private Long coordinator_id;

    private String nickname; // profile

    private String profile_image; // profile

    private String board_image; // board

    private int total_like; // profile

    private int request_count; // profile

    private List<String> styles;
}