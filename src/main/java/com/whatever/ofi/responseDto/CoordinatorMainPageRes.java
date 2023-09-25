package com.whatever.ofi.responseDto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CoordinatorMainPageRes {

    private Long coordinator_id;

    private String nickname; // profile

    private String profile_image; // profile

    private int total_like; // profile

    private int request_count; // profile

    private List<String> styles;
}
