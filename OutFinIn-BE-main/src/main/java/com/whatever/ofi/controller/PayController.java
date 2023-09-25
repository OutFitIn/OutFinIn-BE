package com.whatever.ofi.controller;

import com.whatever.ofi.requestDto.PayRequest;
import kr.co.bootpay.Bootpay;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pay")
@CrossOrigin(origins = {"https://web-frontend-iciy2almolkc88.sel5.cloudtype.app/", "http://localhost:3000"}, allowCredentials = "true")
public class PayController {

    @Value("${bootpay.restapikey}")
    private String rest_api_key;

    @Value("${bootpay.privatekey}")
    private String private_key;

    @PostMapping("/confirm")
    public String confirm(@RequestBody PayRequest dto) {

        String status = "";
        HashMap accessToken;
        try {
            Bootpay bootpay = new Bootpay(rest_api_key, private_key);
            accessToken = bootpay.getAccessToken();

            if(accessToken.get("error_code") != null) { //failed
                System.out.println(accessToken.get("error_code"));
                return "fail";
            }

            HashMap res = bootpay.confirm(dto.getReceipt_id());

            if(res.get("error_code") == null) { //success
                System.out.println("confirm success: " + res);
                if((Integer) res.get("status") == dto.getStatus() && (Integer) res.get("price") == dto.getPrice()) {
                    status = "success";
                }else {
                    status = "fail";
                }
            } else {
                System.out.println("confirm false: " + res);
                status = "fail";
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return status;
    }
}
