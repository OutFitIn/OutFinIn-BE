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
@CrossOrigin(origins = {"https://web-frontend-iciy2almolkc88.sel5.cloudtype.app/", "http://1.233.127.245/"}, allowCredentials = "true")
public class LoginController {
    private final UserService userService;
    private final CoordinatorService coordinatorService;
    private final SocialLoginService socialLoginService;

    @PostMapping("/login") // 여기서 사용자, 코디네이터 id 값 넘기기
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

        return type;
    }

    @GetMapping("/test")
    public String test(HttpSession session) {

        String res = Long.toString((Long) session.getAttribute("id"));
        return res + ((String) session.getAttribute("type"));
    }

   @PostMapping("/kakao")
    public String KakaoLogin (@RequestBody String code ,HttpSession session) {
        if (code == null) return "false";

        String kakaoId = socialLoginService.getKakaoId(code);
        Map<Long, String> data = socialLoginService.socialLogin("Kakao", kakaoId);

        if (data.isEmpty() || data == null) return "false";

        Optional<Map.Entry<Long, String>> firstEntry = data.entrySet().stream().findFirst();

        String Type = "";
        if (firstEntry.isPresent()) {
            Map.Entry<Long, String> entry = firstEntry.get();
            Long id = entry.getKey();
            String type = entry.getValue();
            System.out.println(id + " " + type);
            Type = type;

            String token = socialLoginService.login(id, type);

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
        }

        return Type;
    }

    @PostMapping("/naver")
    public String NaverLogin (@RequestBody String code, HttpSession session) {
        String temp = code.substring(0, code.length() - 1);
        System.out.println("code: " + temp);

        if (code == null) return "false";

        String naverId = socialLoginService.getNaverId(temp);
        Map<Long, String> data = socialLoginService.socialLogin("Naver", naverId);

        if (data.isEmpty() || data == null) return "false";

        Optional<Map.Entry<Long, String>> firstEntry = data.entrySet().stream().findFirst();

        String Type = "";
        if (firstEntry.isPresent()) {
            Map.Entry<Long, String> entry = firstEntry.get();
            Long id = entry.getKey();
            String type = entry.getValue();
            System.out.println(id + " " + type);
            Type = type;

            String token = socialLoginService.login(id, type);

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
        }

        return Type;
    }

    @PostMapping("/google")
    public String GoogleLogin (@RequestBody String code, HttpSession session) {
        String temp = code.substring(0, code.length() - 1);
        System.out.println("code: " + temp);

        if (code == null) return "false";

        String googleId = socialLoginService.getGoogleId(temp);
        Map<Long, String> data = socialLoginService.socialLogin("Google", googleId);

        if (data.isEmpty() || data == null) return "false";

        Optional<Map.Entry<Long, String>> firstEntry = data.entrySet().stream().findFirst();

        String Type = "";
        if (firstEntry.isPresent()) {
            Map.Entry<Long, String> entry = firstEntry.get();
            Long id = entry.getKey();
            String type = entry.getValue();
            System.out.println(id + " " + type);
            Type = type;

            String token = socialLoginService.login(id, type);

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
        }

        return Type;
    }
}
