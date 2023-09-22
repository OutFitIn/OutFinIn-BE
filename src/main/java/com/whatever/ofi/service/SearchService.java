package com.whatever.ofi.service;

import com.whatever.ofi.repository.CoordinatorRepository;
import com.whatever.ofi.repository.SearchRepository;
import com.whatever.ofi.repository.UserRepository;
import com.whatever.ofi.requestDto.SearchRequest;
import com.whatever.ofi.responseDto.CoordinatorWithBoard;
import com.whatever.ofi.responseDto.CoordinatorWithSearch;
import com.whatever.ofi.responseDto.UserMainPageRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchService {

    private final SearchRepository searchRepository;

    public List<UserMainPageRes> searchUser() {
        return searchRepository.findByStyles();
    }


    public List<CoordinatorWithBoard> searchCoordinatorWithBoard(SearchRequest searchRequest) {
        return searchRepository.findCoordinatorWithBoard(searchRequest);
    }

    public List<CoordinatorWithSearch> searchLikeRating(){
        return searchRepository.findLikeRating();
    }

    public List<CoordinatorWithSearch> searchCountRating(){
        return searchRepository.findCountRating();
    }


}
