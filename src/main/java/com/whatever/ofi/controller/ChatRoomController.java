package com.whatever.ofi.controller;

import com.whatever.ofi.config.Util;
import com.whatever.ofi.domain.ChatRoom;
import com.whatever.ofi.repository.ChatRoomRepository;
import com.whatever.ofi.responseDto.ChatRoomListRes;
import com.whatever.ofi.responseDto.MessagesResponse;
import com.whatever.ofi.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "https://web-outfinin-fe-iciy2almolkc88.sel5.cloudtype.app/", allowCredentials = "true")
public class ChatRoomController {
    @Value("${jwt.secret}")
    private String secretKey;
    private final ChatService chatService;
    private final Util util;

    //방만들기
    @GetMapping("/room") // 원래 POST 였음 물어보기
    public String createRoom(@RequestParam("coordinatorNickname") String yournickname, @CookieValue("token") String token) {
        String mynickName = util.getNickname(token, secretKey);
        System.out.print(mynickName);
        return chatService.createChatRoom(mynickName, yournickname);
    }

    //읽음 처리 기능
    @GetMapping("/readChat")
    public void readMessage(Long chatid){
        chatService.readChat(chatid);
    }

    //message가져오기
    @GetMapping("/messages")
    public MessagesResponse getMessages(@CookieValue("token") String token,
                                        @RequestParam("roomId") String roomid,
                                        @RequestParam("nickname") String yournickname,
                                        HttpSession session){

        String myNickname = util.getNickname(token, secretKey);

        if(session.getAttribute("type") == "user") {
            return chatService.getMessages(myNickname, roomid, yournickname);
        }else {
            return chatService.getMessages(yournickname, roomid, myNickname);
        }
    }

    // 현재 사용자의 전체 채팅룸 반환
    @GetMapping("/main")
    public List<ChatRoomListRes> getChatRooms(@CookieValue("token") String token){
        String myNickname = util.getNickname(token, secretKey);
        List<ChatRoom> chatRooms = chatService.getChatingRooms(myNickname);

        List<ChatRoomListRes> response = new ArrayList<>();

        for(ChatRoom room : chatRooms) {
            ChatRoomListRes res = ChatRoomListRes.builder()
                    .room_id(room.getRoomId())
                    .coordinator(room.getOuter())
                    .user(room.getFiter())
                    .history(room.getHistories())
                    .build();

            response.add(res);
        }

        return response;
    }

    @GetMapping("/all")
    public List<ChatRoom> getAllRooms(){
        return chatService.getAllRooms();
    }

    @GetMapping("/find")
    public ChatRoom findRoom(String UUID){
        return chatService.findRoom(UUID);
    }
}
