package com.whatever.ofi.controller;

import com.whatever.ofi.requestDto.BoardRequest;
import com.whatever.ofi.service.SocialLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/social")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class SocialLoginController {

    private final SocialLoginService socialLoginService;

    // 게시물 저장
    @PostMapping("/kakao")
    public String RegisterKakao(@RequestBody String code, HttpSession session) {
        /*Long id = (Long) session.getAttribute("id");
        String type = session.getAttribute("type").toString();*/

        Long id = 1L;
        String type = "user";

        String kakaoId = socialLoginService.getRegisterKakaoId(code);

        socialLoginService.saveKakao(id, type, kakaoId);

        return "success";
    }

    @PostMapping("/naver")
    public String RegisterNaver(@RequestBody String code, HttpSession session) {
        /*Long id = (Long) session.getAttribute("id");
        String type = session.getAttribute("type").toString();*/
        String temp = code.substring(0, code.length() - 1);
        System.out.println("code: " + temp);

        Long id = 2L;
        String type = "coordinator";

        String naverId = socialLoginService.getRegisterNaverId(temp);

        socialLoginService.saveNaver(id, type, naverId);

        return "success";
    }

    @PostMapping("/google")
    public String RegisterGoogle(@RequestBody String code, HttpSession session) {
        /*Long id = (Long) session.getAttribute("id");
        String type = session.getAttribute("type").toString();*/
        String temp = code.substring(0, code.length() - 1);
        System.out.println("code: " + temp);

        Long id = 1L;
        String type = "coordinator";

        String googleId = socialLoginService.getRegisterGoogleId(temp);

        socialLoginService.saveGoogle(id, type, googleId);

        return "success";
    }
}
