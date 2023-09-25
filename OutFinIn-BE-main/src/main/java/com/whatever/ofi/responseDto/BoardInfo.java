package com.whatever.ofi.responseDto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BoardInfo {
    private Long BoardId;
    private String BoardImage;
    private Integer BoardlikeCount;
    private String Boardcontent;
}
