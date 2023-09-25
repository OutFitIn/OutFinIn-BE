package com.whatever.ofi.responseDto;

import com.whatever.ofi.domain.ChatHistory;
import com.whatever.ofi.domain.ChatRoom;
import com.whatever.ofi.domain.Coordinator;
import com.whatever.ofi.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ChatRoomListRes {

    private String room_id;

    private List<User> user = new ArrayList<>();

    private List<Coordinator> coordinator = new ArrayList<>();

    private List<ChatHistory> history;

    @Builder
    public ChatRoomListRes(String room_id,
                           User user,
                           Coordinator coordinator,
                           List<ChatHistory> history) {
        this.room_id = room_id;
        this.user.add(user);
        this.coordinator.add(coordinator);
        this.history = history;
    }
}
