package com.whatever.ofi.responseDto;

import com.whatever.ofi.domain.ChatRoom;
import com.whatever.ofi.domain.Coordinator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class MessagesResponse {
    private SimpleUserResponse you;
    private List<ChatHistoryResponse> chatHistories;


    public MessagesResponse(SimpleUserResponse you, List<ChatHistoryResponse> chatHistories) {
        this.you = you;
        this.chatHistories = chatHistories;
    }

    public static MessagesResponse of(Coordinator you, ChatRoom chatRoom) {
        return new MessagesResponse(
                SimpleUserResponse.of(you),
                chatRoom.getHistories().stream()
                        .map(ChatHistoryResponse::of)
                        .collect(Collectors.toList())
        );
    }
}