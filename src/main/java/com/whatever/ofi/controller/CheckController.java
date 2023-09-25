package com.whatever.ofi.controller;

import com.whatever.ofi.service.CheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/check")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CheckController {

    private final CheckService checkService;

    @GetMapping("/nickname")
    public String validateDuplicateNickname(@RequestParam String nickname) {
        return checkService.availableNickname(nickname) ? "available" : "duplicate";
    }

    @GetMapping("/email")
    public String validateDuplicateEmail(@RequestParam String email) {
        return checkService.availableEmail(email) ? "available" : "duplicate";
    }
}
