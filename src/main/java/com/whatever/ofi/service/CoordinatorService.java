package com.whatever.ofi.service;

import com.whatever.ofi.config.Util;
import com.whatever.ofi.domain.Board;
import com.whatever.ofi.domain.Coordinator;
import com.whatever.ofi.domain.User;
import com.whatever.ofi.requestDto.*;
import com.whatever.ofi.repository.CoordinatorRepository;
import com.whatever.ofi.responseDto.CoordinatorAllBoardRes;
import com.whatever.ofi.responseDto.CoordinatorMainPageRes;
import com.whatever.ofi.responseDto.CoordinatorMyPageRes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class  CoordinatorService {

    private final CoordinatorRepository coordinatorRepository;

    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret}")
    private String secretKey;

    @Transactional
    public void join(Coordinator coordinator) {
        coordinatorRepository.save(coordinator);
    }

    public Coordinator findOne(Long id) {
        return coordinatorRepository.findOne(id);
    }

    public Long findId(String email) {
        return coordinatorRepository.findId(email);
    }

    public String login(LoginRequest loginRequest) {

        // 1. Id가 틀린 경우
        if(coordinatorRepository.findByEmail(loginRequest.getEmail()).isEmpty()) return "Email Not Found";

        // 2. Pw가 틀린 경우
        Coordinator coordinator = coordinatorRepository.findByPassword(loginRequest.getEmail());

        // 사용자가 입력한 비밀번호 (rawPassword)와 암호화된 비밀번호 (hashedPassword)를 비교합니다.
        if(!encoder.matches(loginRequest.getPassword(), coordinator.getPassword())) return "Password Not Equal";

        String nickname = coordinator.getNickname();

        return Util.createJwt(nickname, secretKey);
    }

    // 코디 네이터 마이 페이지
    public CoordinatorMyPageRes findMyPage(Long id) {
        return coordinatorRepository.findMyPage(id);
    }

    public List<CoordinatorAllBoardRes> findAllBoard(Long id) {
        return coordinatorRepository.findAllBoard(id);
    }

    @Transactional
    public String editProfile(CoordinatorEditRequest dto, Long coordinatorId) {
        Coordinator coordinator = coordinatorRepository.findOne(coordinatorId);
        coordinator.edit(dto); // 변경 감지

        return "success";
    }
}
