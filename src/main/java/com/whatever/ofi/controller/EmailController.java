package com.whatever.ofi.controller;

import com.whatever.ofi.requestDto.EmailAuthDto;
import com.whatever.ofi.service.CheckService;
import com.whatever.ofi.service.EmailAuthService;
import com.whatever.ofi.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email/code")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class EmailController {

    // 이메일 인증
    private final MailService mailService;

    private final CheckService checkService;

    private final EmailAuthService emailAuthService;

    @GetMapping("/send")
    public String mailConfirm(@RequestParam String email) throws Exception {
        boolean pass = checkService.availableEmail(email);

        if(!pass) {
            return "duplicate";
        }

        String code = mailService.sendSimpleMessage(email);
        System.out.println("인증코드 : " + code);

        emailAuthService.saveDataWithExpiration(email, code, 300);
        return code;
    }

    @PostMapping("/auth")
    public String mailAuth(@RequestBody EmailAuthDto dto) throws Exception {

        try {
            if(emailAuthService.getData(dto.getEmail()).equals(dto.getCode())) {
                return "success!";
            } else {
                return "false";
            }
        } catch (NullPointerException e) {
            System.out.println("인증 코드 시간이 만료되었습니다.");
            return "timeout";
        }
    }
}
