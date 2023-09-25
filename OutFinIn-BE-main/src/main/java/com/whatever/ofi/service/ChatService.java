package com.whatever.ofi.service;

import com.whatever.ofi.domain.ChatHistory;
import com.whatever.ofi.domain.ChatRoom;
import com.whatever.ofi.domain.Coordinator;
import com.whatever.ofi.repository.ChatHistoryRepository;
import com.whatever.ofi.repository.ChatRoomRepository;
import com.whatever.ofi.repository.CoordinatorRepository;
import com.whatever.ofi.repository.UserRepository;
import com.whatever.ofi.requestDto.ChatMessage;
import com.whatever.ofi.responseDto.MessagesResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import com.whatever.ofi.domain.User;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ChatHistoryRepository chatHistoryRepository;
    private final CoordinatorRepository coordinatorRepository;

    private ChatRoom findChatRoom(User findBuyer, Coordinator findSeller) {
        return chatRoomRepository.findChatRoomByUsers(findBuyer, findSeller);
    }

    @Transactional
    public String createChatRoom(String myNickName,String yourNickName) {
        User fiter = getUserByNickname(myNickName); //User에서 찾아야 하는거고
        Coordinator outer = getCoordinatorByNickname(yourNickName); //Coordinator에서 찾아야 하는거고

        System.out.println(fiter.getNickname());
        System.out.println(outer.getNickname());

        ChatRoom chatRoom = findChatRoom(fiter, outer);

        if(chatRoom == null){
            chatRoom = ChatRoom.create(fiter ,outer);
            chatRoomRepository.save(chatRoom);
        }
        return chatRoom.getRoomId();
    }

    @Transactional
    public Long saveMessage(ChatMessage message) {
        ChatRoom chatRoom = findRoomId(message.getRoomId());
        List<User> findUser = userRepository.findNickname(message.getSender());
        User sender = findUser.isEmpty() ? null : findUser.get(0);

        String _message = message.getMessage();
        LocalDateTime createdAt = message.getCreateAt();
        Coordinator send = null;
        if (sender == null){
            send = getCoordinatorByNickname(message.getSender());
            return saveChatMessage(chatRoom, null, send, _message, createdAt);
        }
        return saveChatMessage(chatRoom, sender, null,_message, createdAt);
    }

    public ChatRoom findRoomId(String roomid) {
        return chatRoomRepository.findChatRoomByRoomId(roomid);
    }

    public User getUserByNickname(String userNickname) {
        return userRepository.findByNickName(userNickname);
    }

    public Coordinator getCoordinatorByNickname(String userNickname) {
        return coordinatorRepository.findByNickName(userNickname);
    }

    @Transactional
    public Long saveChatMessage(ChatRoom chatRoom, User sender, Coordinator send, String message, LocalDateTime createdAt) {
        ChatHistory chatHistory;

        if(sender == null){
            chatHistory = ChatHistory.create(chatRoom, null, send, message, createdAt);
        }
        else {
            chatHistory = ChatHistory.create(chatRoom, sender, null, message, createdAt);
        }

        Long chat_id = chatHistoryRepository.save(chatHistory);
        return chat_id;
    }

    public void readChat(Long chatid){
        ChatHistory chatHistory = chatHistoryRepository.findById(chatid);
        if(chatHistory != null){
            chatHistory.setReadCount(chatHistory.getReadCount() - 1);
        }
    }

    public MessagesResponse getMessages(String myNickname, String roomUUID, String yourNickname) {
        User fiter = getUserByNickname(myNickname);
        Coordinator outer = getCoordinatorByNickname(yourNickname);
        ChatRoom chatRoom = findRoomId(roomUUID);
        //reduceReadCountOfChats(chatRoom, fiter);
        return MessagesResponse.of(outer, chatRoom);
    }

    public void reduceReadCountOfChats(ChatRoom chatRoom, User me) {
        chatRoom.getHistories().forEach(chatHistory -> checkAndReduceReadCount(chatHistory, me));
    }

    public void checkAndReduceReadCount(ChatHistory chatHistory, User me) {
        if (canReduceReadCount(chatHistory, me)) {
            chatHistory.setReadCount(chatHistory.getReadCount() - 1);
        }
    }

    public boolean canReduceReadCount(ChatHistory chat,User me) {
        return !chat.getSender().equals(me) && chat.getReadCount() == 1;
    }

    public List<ChatRoom> getChatingRooms(String nickname){
        List<User> findUser = userRepository.findNickname(nickname);
        User user = findUser.isEmpty() ? null : findUser.get(0);

        Coordinator coordinator = null;
        List<ChatRoom> chatRoom;

        if (user == null){
            coordinator = getCoordinatorByNickname(nickname);
            chatRoom = chatRoomRepository.findChatRoomsByCoordinator(coordinator);
        }else {
            chatRoom = chatRoomRepository.findChatRoomsByUser(user);
        }

        return chatRoom;
    }

    public List<ChatRoom> getAllRooms(){
        return chatRoomRepository.findAllRooms();
    }

    public ChatRoom findRoom(String RoomId){
        return chatRoomRepository.findChatRoomByRoomId(RoomId);
    }
}