package com.whatever.ofi.init;

import com.whatever.ofi.Enum.Gender;
import com.whatever.ofi.Enum.Shape;
import com.whatever.ofi.requestDto.BoardRequest;
import com.whatever.ofi.requestDto.CoordinatorProfileRequest;
import com.whatever.ofi.requestDto.UserProfileRequest;
import com.whatever.ofi.service.BoardService;
import com.whatever.ofi.service.CoordinatorService;
import com.whatever.ofi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserInit {

    private final UserService userService;

    private final CoordinatorService coordinatorService;

    private final BCryptPasswordEncoder encoder;

    @PostConstruct
    public void userInit() {
        UserProfileRequest dto1 = new UserProfileRequest("tes1@naver.com", encoder.encode("qwer1234"),
                "테스트1", 170, 60, Gender.MALE, Shape.NATURAL, new ArrayList<>(List.of("미니멀", "힙합", "스포티")));

        UserProfileRequest dto2 = new UserProfileRequest("test2@naver.com", encoder.encode("qwer1234"),
                "테스트2", 165, 55, Gender.FEMALE, Shape.WAVE, new ArrayList<>(List.of("아메카지", "캐주얼")));

        UserProfileRequest dto3 = new UserProfileRequest("test3@naver.com", encoder.encode("qwer1234"),
                "테스트3", 180, 75, Gender.MALE, Shape.STRAIGHT, new ArrayList<>(List.of("러블리", "시티보이", "스트릿")));

        UserProfileRequest dto4 = new UserProfileRequest("test4@naver.com", encoder.encode("qwer1234"),
                "테스트4", 160, 50, Gender.FEMALE, Shape.NATURAL, new ArrayList<>(List.of("이지캐주얼", "유니크")));

        UserProfileRequest dto5 = new UserProfileRequest("test5@naver.com", encoder.encode("qwer1234"),
                "테스트5", 175, 70, Gender.MALE, Shape.WAVE, new ArrayList<>(List.of("힙합", "레트로")));

        userService.join(dto1.toEntity());
        userService.join(dto2.toEntity());
        userService.join(dto3.toEntity());
        userService.join(dto4.toEntity());
        userService.join(dto5.toEntity());
    }

}
