package com.whatever.ofi.repository;

import com.fasterxml.jackson.annotation.JsonTypeId;
import com.whatever.ofi.domain.Board;
import com.whatever.ofi.responseDto.BoardDetailRes;
import com.whatever.ofi.responseDto.UserBoardLikeRes;
import com.whatever.ofi.responseDto.UserLikeTotalRes;
import com.whatever.ofi.responseDto.UserMainPageRes;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BoardRepository {
    @PersistenceContext
    EntityManager em;

    public Board findOne(Long id) {
       return em.find(Board.class, id);
    }

    public Long save(Board board) {
        em.persist(board);
        return board.getId();
    }

    public BoardDetailRes findBoardDetail(Long id) {
        Object[] result =  em.createQuery(
                        " select c.nickname, c.image_url, b.image_url, b.like_count, c.request_count, b.style, b.content, b.season, b.situation, b.title, c.sns_url " +
                                " from Board b, Coordinator c " +
                                " where b.coordinator.id = c.id and b.id = :id ", Object[].class)
                .setParameter("id", id)
                .getSingleResult();

        List<Long> likes = em.createQuery(
                "select b.board.id from BoardLike b " +
                        "where b.user.id = :id", Long.class)
                .setParameter("id", id)
                .getResultList();

        return BoardDetailRes.builder()
                .nickname((String) result[0])
                .profile_image((String) result[1])
                .board_image((String) result[2])
                .like_count((Integer) result[3])
                .request_count((Integer) result[4])
                .style((String) result[5])
                .content((String) result[6])
                .season((String) result[7])
                .situation((String) result[8])
                .title((String) result[9])
                .sns_url((String) result[10])
                .like_status(!likes.isEmpty())
                .build();
    }

    public UserLikeTotalRes findBoardLike(Long id) {
        List<Object[]> resultList = em.createQuery(
                "select b.board.id, b.board.title, b.board.image_url " +
                        "from BoardLike b " +
                        "where b.user.id = :id", Object[].class)
                .setParameter("id", id)
                .getResultList();

        List<Long> likes = em.createQuery(
                "select b.board.id from BoardLike b " +
                        "where b.user.id =: id ", Long.class)
                .setParameter("id", id)
                .getResultList();

        List<UserBoardLikeRes> boards = new ArrayList<>();

        for(Object[] result : resultList) {
            boards.add(new UserBoardLikeRes(
                    (Long) result[0], (String) result[1], (String) result[2]));
        }

        return UserLikeTotalRes.builder()
                .boards(boards)
                .user_board_like(likes)
                .build();
    }
}
