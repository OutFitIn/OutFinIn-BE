package com.whatever.ofi.controller;

import com.whatever.ofi.Enum.Gender;
import com.whatever.ofi.domain.Coordinator;
import com.whatever.ofi.requestDto.*;
import com.whatever.ofi.responseDto.*;
import com.whatever.ofi.service.BoardService;
import com.whatever.ofi.service.CoordinatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coordinator")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CoordinatorController {

    private final CoordinatorService coordinatorService;

    private final BoardService boardService;

    private final BCryptPasswordEncoder encoder;

    @PostMapping("/register")
    public String register(@RequestBody CoordinatorRequest dto, HttpSession session) {
        String encodePassword = encoder.encode(dto.getPassword());

        session.setAttribute("email", dto.getEmail());
        session.setAttribute("password", encodePassword);
        System.out.println(encodePassword);
        return "success";
    }

    @PostMapping("/profile")
    public String createProfile(@RequestBody CoordinatorInfoRequest dto, HttpSession session) {
        session.setAttribute("nickname", dto.getNickname());
        session.setAttribute("sns_url", dto.getSns_url());
        session.setAttribute("image_url", dto.getImage_url());
        session.setAttribute("content", dto.getContent());
        session.setAttribute("gender", dto.getGender());
        session.setAttribute("height", dto.getHeight());
        session.setAttribute("weight", dto.getWeight());
        session.setAttribute("total_like", dto.getTotal_like());
        session.setAttribute("request_count", dto.getRequest_count());
        return "success";
    }

    @PostMapping("/styles")
    public String addStyle(@RequestBody CoordinatorStyleRequest dto, HttpSession session) {
        Coordinator coordinator = Coordinator.builder()
                .styles(dto.getStyles())
                .nickname((String) session.getAttribute("nickname"))
                .email((String) session.getAttribute("email"))
                .password((String) session.getAttribute("password"))
                .sns_url((String) session.getAttribute("sns_url"))
                .image_url((String) session.getAttribute("image_url"))
                .total_like((Integer) session.getAttribute("total_like"))
                .request_count((Integer) session.getAttribute("request_count"))
                .height((Integer) session.getAttribute("height"))
                .weight((Integer) session.getAttribute("weight"))
                .gender((Gender) session.getAttribute("gender"))
                .content((String) session.getAttribute("content"))
                .build();

        session.invalidate(); // 세션에 있는 속성 삭제
        coordinatorService.join(coordinator);
        return "success";
    }

    @GetMapping("/page")//코디네이터 눌렀을 때 상세 정보 페이지 정보들
    public CoordinatorDetailRes showCoordinator(HttpSession session) {
        CoordinatorDetailRes coordinatorDetailRes = new CoordinatorDetailRes();

        CoordinatorMyPageRes coordinatorMyPageRes = coordinatorService.findMyPage((Long) session.getAttribute("id"));
        List<CoordinatorAllBoardRes> boards = coordinatorService.findAllBoard((Long) session.getAttribute("id"));

        coordinatorDetailRes.setNickname(coordinatorMyPageRes.getNickname());
        coordinatorDetailRes.setProfile_image(coordinatorMyPageRes.getImage_url());
        coordinatorDetailRes.setContent(coordinatorMyPageRes.getContent());
        coordinatorDetailRes.setStyles(coordinatorMyPageRes.getStyles());
        coordinatorDetailRes.setRequest_count(coordinatorMyPageRes.getRequest_count());
        coordinatorDetailRes.setTotal_like(coordinatorMyPageRes.getTotal_like());
        coordinatorDetailRes.setBoards(boards);

        return coordinatorDetailRes;
    }

    @GetMapping("/mypage")
    public CoordinatorMyPageRes showMyPage(HttpSession session) {
        return coordinatorService.findMyPage((Long) session.getAttribute("id"));
    }


    @GetMapping("/board/all")
    public List<CoordinatorAllBoardRes> allBoard(HttpSession session) {

        return coordinatorService.findAllBoard((Long) session.getAttribute("id"));
    }

    @PostMapping("/edit")
    public String editProfile(@RequestBody CoordinatorEditRequest dto, HttpSession session) {

        return coordinatorService.editProfile(dto, (Long) session.getAttribute("id"));
    }
}
