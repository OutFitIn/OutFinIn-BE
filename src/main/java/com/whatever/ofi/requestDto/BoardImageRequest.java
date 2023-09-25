package com.whatever.ofi.requestDto;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class BoardImageRequest {
    private Long board_id;
    private List<String> image_url;
}
