package com.whatever.ofi.init;

import com.whatever.ofi.Enum.Gender;
import com.whatever.ofi.requestDto.CoordinatorProfileRequest;
import com.whatever.ofi.service.CoordinatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component("coordinatorInit")
@RequiredArgsConstructor
public class CoordinatorInit {
    private final CoordinatorService coordinatorService;

    private final BCryptPasswordEncoder encoder;
    @PostConstruct
    public void coordinatorInit() {
        CoordinatorProfileRequest dto1 = new CoordinatorProfileRequest("test6@naver.com", encoder.encode("qwer1234"),
                "테스트6", "www.naver.com", "test1.jpg", "잘부탁드립니다~!", Gender.MALE,  170, 60, 942, 24,  new ArrayList<>(List.of("미니멀", "힙합", "스포티")));

        CoordinatorProfileRequest dto2 = new CoordinatorProfileRequest("test7@naver.com", encoder.encode("qwer1234"),
                "테스트7", "www.google.com", "test2.jpg", "환영합니다!", Gender.FEMALE, 165, 55, 865, 28, new ArrayList<>(List.of("아메카지", "캐주얼")));

        CoordinatorProfileRequest dto3 = new CoordinatorProfileRequest("test8@naver.com", encoder.encode("qwer1234"),
                "테스트8", "www.yahoo.com", "test3.jpg", "안녕하세요!", Gender.MALE, 180, 75, 721, 31, new ArrayList<>(List.of("러블리", "시티보이", "스트릿")));

        CoordinatorProfileRequest dto4 = new CoordinatorProfileRequest("test9@naver.com", encoder.encode("qwer1234"),
                "테스트9", "www.bing.com", "test4.jpg", "반갑습니다!", Gender.FEMALE, 160, 50, 638, 329, new ArrayList<>(List.of("이지캐주얼", "유니크")));

        CoordinatorProfileRequest dto5 = new CoordinatorProfileRequest("test10@naver.com", encoder.encode("qwer1234"),
                "테스트10", "www.duckduckgo.com", "test5.jpg", "만나서 기쁩니다!", Gender.MALE, 175, 70, 799, 127, new ArrayList<>(List.of("힙합", "레트로")));


        coordinatorService.join(dto1.toEntity());
        coordinatorService.join(dto2.toEntity());
        coordinatorService.join(dto3.toEntity());
        coordinatorService.join(dto4.toEntity());
        coordinatorService.join(dto5.toEntity());
    }
}
