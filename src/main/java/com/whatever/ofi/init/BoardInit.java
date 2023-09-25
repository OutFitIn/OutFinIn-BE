package com.whatever.ofi.init;

import com.whatever.ofi.requestDto.BoardRequest;
import com.whatever.ofi.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@DependsOn(value = {"coordinatorInit"})
@RequiredArgsConstructor
public class BoardInit {
    private final BoardService boardService;

    @PostConstruct
    public void boardInit() {
        BoardRequest dto1 = new BoardRequest(1L, "힙합", 12, "가을",
                "일상", "가을 코디입니다~", "test1.jpg", "선선한 가을 코디 한방에 정리~");

        BoardRequest dto2 = new BoardRequest(1L, "로맨틱", 8, "봄",
                "데이트", "봄날의 데이트룩~", "test2.jpg", "산듯한 봄 코디 한방에 정리~");

        BoardRequest dto3 = new BoardRequest(2L, "클래식", 6, "겨울",
                "일상", "겨울의 클래식 스타일~", "test3.jpg", "추운 겨울 코디 한방에 정리~");

        BoardRequest dto4 = new BoardRequest(2L, "스트릿", 15, "여름",
                "스트릿패션", "여름에 어울리는 스트릿룩~", "test4.jpg", "핫한 여름 코디 한방에 정리~");

        BoardRequest dto5 = new BoardRequest(3L, "캐주얼", 10, "봄",
                "일상", "봄의 캐주얼 스타일~", "test5.jpg", "산듯한 봄 코디 한방에 정리~");

        BoardRequest dto6 = new BoardRequest(3L, "러블리", 9, "가을",
                "일상", "가을의 러블리 스타일~", "test6.jpg", "선선한 가을 코디 한방에 정리~");

        BoardRequest dto7 = new BoardRequest(4L, "모던", 7, "겨울",
                "일상", "겨울의 모던 스타일~", "test7.jpg", "추운 겨울 코디 한방에 정리~");

        BoardRequest dto8 = new BoardRequest(4L, "헌팅", 11, "여름",
                "헌팅패션", "여름에 어울리는 헌팅룩~", "test8.jpg", "핫한 여름 코디 한방에 정리~");

        BoardRequest dto9 = new BoardRequest(5L, "아메카지", 14, "봄",
                "일상", "봄의 아메카지 스타일~", "test9.jpg", "산듯한 봄 코디 한방에 정리~");

        BoardRequest dto10 = new BoardRequest(5L, "플로럴", 13, "가을",
                "일상", "가을의 플로럴 스타일~", "test10.jpg", "선선한 가을 코디 한방에 정리~");

        boardService.join(dto1, 1L);
        boardService.join(dto2, 1L);
        boardService.join(dto3, 2L);
        boardService.join(dto4, 2L);
        boardService.join(dto5, 3L);
        boardService.join(dto6, 3L);
        boardService.join(dto7, 4L);
        boardService.join(dto8, 4L);
        boardService.join(dto9, 5L);
        boardService.join(dto10, 5L);
    }
}
