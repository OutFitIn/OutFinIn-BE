package com.whatever.ofi.controller;

import com.whatever.ofi.requestDto.LoginRequest;
import com.whatever.ofi.service.CoordinatorService;
import com.whatever.ofi.service.SocialLoginService;
import com.whatever.ofi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class LoginController {
    private final UserService userService;
    private final CoordinatorService coordinatorService;
    private final SocialLoginService socialLoginService;

    @PostMapping("") // 여기서 사용자, 코디네이터 id 값 넘기기
    public String login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        String token = userService.login(loginRequest);
        String type;
        Long id;

        if(token == "Email Not Found" || token == "Password Not Equal") {
            System.out.println("Not user" + token + " " + loginRequest.getPassword());

            token = coordinatorService.login(loginRequest);

            if(token == "Email Not Found" || token == "Password Not Equal") {
                return token;
            }else {
                id = coordinatorService.findId(loginRequest.getEmail());
                type = "coordinator";
            }
        }else {
            id = userService.findId(loginRequest.getEmail());
            type = "user";
        }

        Cookie cookie = new Cookie("token", token);

        cookie.setPath("/");
        cookie.setSecure(false);
        cookie.setMaxAge(86400); // 1일
        cookie.setHttpOnly(false);

        System.out.println(cookie.getValue());

        session.setAttribute("id", id);
        session.setAttribute("type", type);

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.addCookie(cookie);

        return "success";
    }

    @PostMapping("/kakao")
    public String KakaoLogin (@RequestBody String code ,HttpSession session) {
        System.out.println(code);

        if (code == null) return "fail";

        String kakaoId = socialLoginService.getKakaoId(code);
        Map<Long, String> data = socialLoginService.socialLogin("Kakao", kakaoId);

        if (data.isEmpty()) return "fail";

        Optional<Map.Entry<Long, String>> firstEntry = data.entrySet().stream().findFirst();

        if (firstEntry.isPresent()) {
            Map.Entry<Long, String> entry = firstEntry.get();
            Long key = entry.getKey();
            String value = entry.getValue();

            String token = socialLoginService.login(key, value);

            Cookie cookie = new Cookie("token", token);

            cookie.setPath("/");
            cookie.setSecure(false);
            cookie.setMaxAge(86400); // 1일
            cookie.setHttpOnly(false);

            System.out.println(cookie.getValue());

            session.setAttribute("id", key);
            session.setAttribute("type", value);

            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            response.addCookie(cookie);
        }

        return "success";
    }

    @PostMapping("/naver")
    public String NaverLogin (@RequestBody String code, HttpSession session) {
        String temp = code.substring(0, code.length() - 1);
        System.out.println("code: " + temp);

        if (code == null) return "fail";

        String naverId = socialLoginService.getNaverId(temp);
        Map<Long, String> data = socialLoginService.socialLogin("Naver", naverId);

        if (data.isEmpty() || data == null) return "fail";

        if (data.isEmpty()) return "fail";

        Optional<Map.Entry<Long, String>> firstEntry = data.entrySet().stream().findFirst();

        if (firstEntry.isPresent()) {
            Map.Entry<Long, String> entry = firstEntry.get();
            Long key = entry.getKey();
            String value = entry.getValue();

            String token = socialLoginService.login(key, value);

            Cookie cookie = new Cookie("token", token);

            cookie.setPath("/");
            cookie.setSecure(false);
            cookie.setMaxAge(86400); // 1일
            cookie.setHttpOnly(false);

            System.out.println(cookie.getValue());

            session.setAttribute("id", key);
            session.setAttribute("type", value);

            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            response.addCookie(cookie);
        }

        return data.toString();
    }

    @PostMapping("/google")
    public String GoogleLogin (@RequestBody String code, HttpSession session) {
        String temp = code.substring(0, code.length() - 1);
        System.out.println("code: " + temp);

        if (code == null) return "fail";

        String googleId = socialLoginService.getGoogleId(temp);
        Map<Long, String> data = socialLoginService.socialLogin("Google", googleId);

        if (data.isEmpty()) return "fail";

        if (data.isEmpty()) return "fail";

        Optional<Map.Entry<Long, String>> firstEntry = data.entrySet().stream().findFirst();

        if (firstEntry.isPresent()) {
            Map.Entry<Long, String> entry = firstEntry.get();
            Long key = entry.getKey();
            String value = entry.getValue();

            String token = socialLoginService.login(key, value);

            Cookie cookie = new Cookie("token", token);

            cookie.setPath("/");
            cookie.setSecure(false);
            cookie.setMaxAge(86400); // 1일
            cookie.setHttpOnly(false);

            System.out.println(cookie.getValue());

            session.setAttribute("id", key);
            session.setAttribute("type", value);

            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            response.addCookie(cookie);
        }

        return data.toString();
    }

    @GetMapping("/test")
    public String test(HttpSession session) {

        String res = Long.toString((Long) session.getAttribute("id"));
        return res + ((String) session.getAttribute("type"));
    }
}
