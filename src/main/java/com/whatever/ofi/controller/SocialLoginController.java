package com.whatever.ofi.controller;

import com.whatever.ofi.config.Util;
import com.whatever.ofi.service.SocialLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth/register")
@CrossOrigin(origins = "https://web-outfinin-fe-iciy2almolkc88.sel5.cloudtype.app/", allowCredentials = "true")
public class SocialLoginController {

    private final SocialLoginService socialLoginService;
    private final Util util;

    // 게시물 저장
    @PostMapping("/kakao")
    public String RegisterKakao(@RequestBody String code, @RequestHeader("Authorization") String token) {

        String temp = code.substring(0, code.length() - 1);
        System.out.println("code: " + temp);

        String secretKey = "GDGWHATEVEROFI";
        Long id = util.getUserId(token, secretKey);
        String type = util.getType(token, secretKey);

        if(id == null || type == null || token == null) {
            return "false";
        }

        String kakaoId = socialLoginService.getRegisterKakaoId(temp);

        socialLoginService.saveKakao(id, type, kakaoId);

        return id.toString() + " " + type;
    }

    @PostMapping("/naver")
    public String RegisterNaver(@RequestBody String code, @RequestHeader("Authorization") String token) {
        System.out.println("Naver Token:" + token);

        String temp = code.substring(0, code.length() - 1);
        System.out.println("code: " + temp);

        Long id = util.getUserId(token, "GDGWHATEVEROFI");
        String type = util.getType(token, "GDGWHATEVEROFI");

        if(id == null || type == null || token == null) {
            return "false";
        }

        System.out.println(id + " " + type);

        String NaverId = socialLoginService.getRegisterNaverId(temp);

        socialLoginService.saveNaver(id, type, NaverId);

        return id.toString() + " " + type;
    }

    @PostMapping("/google")
    public String RegisterGoogle(@RequestBody String code, @RequestHeader("Authorization") String token) {
        System.out.println("Google Token:" + token);

        String temp = code.substring(0, code.length() - 1);
        System.out.println("code: " + temp);

        Long id = util.getUserId(token, "GDGWHATEVEROFI");
        String type = util.getType(token, "GDGWHATEVEROFI");

        if(id == null || type == null || token == null) {
            return "false";
        }

        System.out.println(id + " " + type);

        String GoogleId = socialLoginService.getRegisterGoogleId(temp);

        socialLoginService.saveNaver(id, type, GoogleId);

        return id.toString() + " " + type;
    }

    @PostMapping("/testgoogle")
    public String testGoogle(@RequestBody String code) {
        System.out.println("Google code:" + code);

        String temp = code.substring(0, code.length() - 1);

        String GoogleId = socialLoginService.getGoogleId(temp);

        return GoogleId;
    }
}
