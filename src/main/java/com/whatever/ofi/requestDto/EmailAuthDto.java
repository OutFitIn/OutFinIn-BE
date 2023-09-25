package com.whatever.ofi.requestDto;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "null", timeToLive = 180)
@NoArgsConstructor
public class EmailAuthDto {
    @Id
    private String email;

    private String code;

    public EmailAuthDto(String email, String code) {
        this.email = email;
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
