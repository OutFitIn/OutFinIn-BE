package com.whatever.ofi.responseDto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CoordinatorWithBoard {

    private String coornickname;

    private String coorimageUrl;

    private int coorrequestCount;

    private List<String> coorstyles;

    private String boardseason;

    private String boardsituation;

    private String boardImage;

    private int boardlikeCount;

    private String boardStyle;
}
