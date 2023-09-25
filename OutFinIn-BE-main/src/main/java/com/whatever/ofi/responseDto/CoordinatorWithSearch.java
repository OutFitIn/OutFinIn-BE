package com.whatever.ofi.responseDto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CoordinatorWithSearch {

    private Long coorId;

    private String coornickname;

    private String coorimageUrl;

    private int coorrequestCount;

    private int coorTotalLike;

    private List<String> coorstyles;


    private List<BoardInfo> boardInfos;

    public void setBoardInfos(List<BoardInfo> boardInfos) {
        this.boardInfos = boardInfos;
    }
}
