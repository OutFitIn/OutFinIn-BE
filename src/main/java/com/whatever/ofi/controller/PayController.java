package com.whatever.ofi.controller;

import kr.co.bootpay.Bootpay;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pay")
@CrossOrigin(origins = {"https://web-frontend-iciy2almolkc88.sel5.cloudtype.app/", "http://1.233.127.245/"}, allowCredentials = "true")
public class PayController {

    @Value("${bootpay.restapikey}")
    private String rest_api_key;

    @Value("${bootpay.privatekey}")
    private String private_key;

    @PostMapping("/confirm")
    public void confirm() {
        Bootpay bootpay = new Bootpay(rest_api_key, private_key);
        HashMap accessToken;

        try {
            accessToken = bootpay.getAccessToken();
        }catch(Exception e) {
            e.printStackTrace();
        }


    }
}
