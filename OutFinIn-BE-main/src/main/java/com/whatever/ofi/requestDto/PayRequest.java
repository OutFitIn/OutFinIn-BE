package com.whatever.ofi.requestDto;

import lombok.Getter;

@Getter
public class PayRequest {
    private String receipt_id;

    private int status;

    private int price;
}
