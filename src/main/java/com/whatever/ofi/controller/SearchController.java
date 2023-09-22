package com.whatever.ofi.controller;


import com.whatever.ofi.config.Util;
import com.whatever.ofi.requestDto.SearchRequest;
import com.whatever.ofi.responseDto.CoordinatorWithBoard;
import com.whatever.ofi.responseDto.CoordinatorWithSearch;
import com.whatever.ofi.responseDto.UserMainPageRes;
import com.whatever.ofi.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class SearchController {
    @Value("${jwt.secret}")
    private String secretKey;
    private final SearchService searchService;

    private final Util util;
    @GetMapping("/main")
    public List<UserMainPageRes> getStyle(){
        return searchService.searchUser();
    }

    @PostMapping("/styleSearch")
    public List<CoordinatorWithBoard> getBoard(@RequestBody SearchRequest searchRequest) {
        return searchService.searchCoordinatorWithBoard(searchRequest);
    }

    @GetMapping("/like")
    public List<CoordinatorWithSearch> likeFilter(){
        return searchService.searchLikeRating();
    }

    @GetMapping("/count")
    public List<CoordinatorWithSearch> countFilter(){
        return searchService.searchCountRating();
    }
}