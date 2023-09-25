package com.whatever.ofi.repository;

import com.whatever.ofi.domain.BoardLike;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class BoardLikeRepository {
    @PersistenceContext
    EntityManager em;

    public BoardLike findByUserIdAndBoardId(Long userId, Long boardId) {
        return em.createQuery(
                "select b " +
                        "from BoardLike b " +
                        "where b.board.id = :boardId and b.user.id = :userId", BoardLike.class)
                .setParameter("userId", userId)
                .setParameter("boardId", boardId)
                .getSingleResult();
    }

    public void save(BoardLike boardLike) {
        em.persist(boardLike);
    }

    public void remove(BoardLike boardLike) {
        em.remove(boardLike);
    }
}
