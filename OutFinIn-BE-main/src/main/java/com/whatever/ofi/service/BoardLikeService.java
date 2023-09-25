package com.whatever.ofi.service;

import com.whatever.ofi.domain.Board;
import com.whatever.ofi.domain.BoardLike;
import com.whatever.ofi.domain.User;
import com.whatever.ofi.repository.BoardLikeRepository;
import com.whatever.ofi.repository.BoardRepository;
import com.whatever.ofi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardLikeService {

    private final UserRepository userRepository;

    private final BoardRepository boardRepository;

    private final BoardLikeRepository boardLikeRepository;

    @Transactional
    public void increaseLike(Long userId, Long boardId) {
        User user = userRepository.findOne(userId);
        Board board = boardRepository.findOne(boardId);
        board.addLike(); // 게시물 좋아요 증가, 게시물을 작성한 아우터의 전체 좋아요 증가

        BoardLike boardLike = new BoardLike(user, board);
        user.addBoardLike(boardLike);
        board.addBoardLike(boardLike);
        boardLikeRepository.save(boardLike);
    }

    @Transactional
    public String decreaseLike(Long userId, Long boardId) {
        //User user = userRepository.findOne(userId);
        Board board = boardRepository.findOne(boardId);

        // 게시물 좋아요 증가, 게시물을 작성한 아우터의 전체 좋아요 증가
        if(board.removeLike() == "possible"){
            BoardLike boardLike = boardLikeRepository.findByUserIdAndBoardId(userId, boardId);
            boardLikeRepository.remove(boardLike);
            return "possible";
        }
        else {
            return "impossible";
        }
    }


}
