package com.whatever.ofi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ChatHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private ChatRoom chatRoom;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private User sender;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Coordinator send;
    private String message;

    private int readCount = 2;

    private LocalDateTime createdAt;


    public ChatHistory(ChatRoom chatRoom, User sender, Coordinator send,String message, LocalDateTime createdAt) {
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.send = send;
        this.message = message;
        this.createdAt = createdAt;
    }


    public static ChatHistory create(ChatRoom chatRoom, User sender, Coordinator send, String message, LocalDateTime createdAt) {
        ChatHistory chatHistory = null;
        if(sender == null){
            chatHistory = new ChatHistory(chatRoom, null ,send, message, createdAt);
            chatRoom.getHistories().add(chatHistory);
            return chatHistory;
        }else {
            chatHistory = new ChatHistory(chatRoom, sender, null, message, createdAt);
            chatRoom.getHistories().add(chatHistory);
        }
        return chatHistory;
    }
}