package com.whatever.ofi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whatever.ofi.Enum.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ChatRoom{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fiter_user_id")
    private User fiter;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "outer_user_id")
    private Coordinator outer;

    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.EAGER) // FetchType.LAZY로 변경
    private List<ChatHistory> histories = new ArrayList<>();


    public static ChatRoom create(User fiter, Coordinator outer) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.fiter = fiter;
        chatRoom.outer = outer;
        return chatRoom;
    }
    @Builder
    public ChatRoom(Long id, String roomId, User fiter, Coordinator outer, List<ChatHistory> histories){
        this.id = id;
        this.roomId = roomId;
        this.fiter = fiter;
        this.outer = outer;
        this.histories = histories;
    }
}
