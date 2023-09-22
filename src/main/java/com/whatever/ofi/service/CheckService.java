package com.whatever.ofi.service;

import com.whatever.ofi.repository.CheckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CheckService {

    private final CheckRepository checkRepository;

    public boolean availableNickname(String nickname) {
        List<String> findNickname = checkRepository.findByNickname(nickname);
        return findNickname.isEmpty() ? true : false;
    }

    public boolean availableEmail(String email) {
        List<String> findEmail = checkRepository.findByEmail(email);
        return findEmail.isEmpty() ? true : false;
    }
}
