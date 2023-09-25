package com.whatever.ofi.requestDto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SearchRequest {
    private List<String> styles = new ArrayList<>();
    private List<String> season = new ArrayList<>();
    private List<String> situation = new ArrayList<>();
}
