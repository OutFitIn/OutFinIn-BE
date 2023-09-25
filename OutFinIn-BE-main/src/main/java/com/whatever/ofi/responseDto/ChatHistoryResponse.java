package com.whatever.ofi.responseDto;

import com.whatever.ofi.domain.ChatHistory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ChatHistoryResponse {
    private String senderId;
    private String message;
    private LocalDateTime createdAt;

    public ChatHistoryResponse(String senderId, String message, LocalDateTime createdAt) {
        this.senderId = senderId;
        this.message = message;
        this.createdAt = createdAt;
    }


    public static ChatHistoryResponse of(ChatHistory chatHistory) {
        String senderNickname = chatHistory.getSender() != null ? chatHistory.getSender().getNickname() : chatHistory.getSend().getNickname();
        return new ChatHistoryResponse(
                senderNickname,
                chatHistory.getMessage(),
                chatHistory.getCreatedAt()
        );
    }
}
