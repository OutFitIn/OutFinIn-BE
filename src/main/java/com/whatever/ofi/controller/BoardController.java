package com.whatever.ofi.controller;

import com.whatever.ofi.requestDto.BoardImageRequest;
import com.whatever.ofi.requestDto.BoardRequest;
import com.whatever.ofi.responseDto.BoardDetailRes;
import com.whatever.ofi.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class BoardController {

    private final BoardService boardService;

    // 게시물 저장
    @PostMapping("/create")
    public String createBoard(@RequestBody BoardRequest dto, HttpSession session) {
        boardService.join(dto, (Long) session.getAttribute("id"));
        return "success";
    }

    @PostMapping("/image")
    public String addImage(@RequestBody BoardImageRequest dto) {
        boardService.insertImage(dto);
        return "success";
    }

    // 게시물 눌렀을 때 모든 정보 보기
    @GetMapping("/show")
    public BoardDetailRes showBoard(@RequestParam Long id) {
        return boardService.findBoardDetail(id);
    }
}
