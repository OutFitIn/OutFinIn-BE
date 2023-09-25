package com.whatever.ofi.controller;

import com.whatever.ofi.domain.Board;
import com.whatever.ofi.domain.Coordinator;
import com.whatever.ofi.repository.CoordinatorRepository;
import com.whatever.ofi.responseDto.CoordinatorMainPageRes;
import com.whatever.ofi.responseDto.UserMainPageRes;
import com.whatever.ofi.responseDto.UserMainTotalRes;
import com.whatever.ofi.service.CoordinatorService;
import com.whatever.ofi.service.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
@CrossOrigin(origins = "https://web-frontend-iciy2almolkc88.sel5.cloudtype.app/", allowCredentials = "true")
public class MainPageController {

    private final CoordinatorService coordinatorService;

    private final MainPageService mainPageService;

    // 사용자의 메인 페이지 반환
    @GetMapping("/user")
    public UserMainTotalRes showUserMainPage(HttpSession session) {
        return mainPageService.searchUserMainPage((Long) session.getAttribute("id"));
    }

    // 코디네이터의 메인 페이지 반환
    @GetMapping("/coordinator")
    public List<CoordinatorMainPageRes> showCoordinatorMainPage() {
        return mainPageService.searchCoordinatorMainPage();
    }

}
