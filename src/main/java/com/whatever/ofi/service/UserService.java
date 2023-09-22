package com.whatever.ofi.service;

import com.whatever.ofi.config.Util;
import com.whatever.ofi.domain.Board;
import com.whatever.ofi.domain.BoardLike;
import com.whatever.ofi.domain.User;
import com.whatever.ofi.repository.BoardRepository;
import com.whatever.ofi.requestDto.LoginRequest;
import com.whatever.ofi.requestDto.UserEditRequest;
import com.whatever.ofi.requestDto.UserProfileRequest;
import com.whatever.ofi.requestDto.UserStyleRequest;
import com.whatever.ofi.repository.UserRepository;
import com.whatever.ofi.responseDto.UserBoardLikeRes;
import com.whatever.ofi.responseDto.UserMyPageRes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    @Value("${jwt.secret}")
    private String secretKey;

    private final UserRepository userRepository;

    private final BoardRepository boardRepository;

    private final  BCryptPasswordEncoder encoder;


    @Transactional
    public void join(User user) {
        userRepository.save(user);
    }

    public String login(LoginRequest loginRequest) {

        // 1. Id가 틀린 경우
        if(userRepository.findByEmail(loginRequest.getEmail()).isEmpty()) return "Email Not Found";

        // 2. Pw가 틀린 경우
        User user = userRepository.findByPassword(loginRequest.getEmail());

        // 사용자가 입력한 비밀번호 (rawPassword)와 암호화된 비밀번호 (hashedPassword)를 비교합니다.
        if(!encoder.matches(loginRequest.getPassword(), user.getPassword())) return "Password Not Equal";

        String nickname = user.getNickname();

        return Util.createJwt(nickname, secretKey);
    }

    public Long findId(String email) {
        return userRepository.findId(email);
    }

    public UserMyPageRes findMyPage(Long id) {
        return userRepository.findMyPage(id);
    }

    public List<UserBoardLikeRes> findBoardLike(Long id) {
        return boardRepository.findBoardLike(id);
    }

    @Transactional
    public String editProfile(UserEditRequest dto, Long userId) {
        User user = userRepository.findOne(userId);
        user.edit(dto); // 변경 감지
        return "success";
    }
}
