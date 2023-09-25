package com.whatever.ofi.requestDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessage {

    private String roomId; // 방번호
    private String sender; // 메시지 보낸사람
    private String message; // 메시지
    private LocalDateTime createAt;

}