package com.whatever.ofi.repository;

import com.whatever.ofi.domain.BoardImage;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class BoardImageRepository {
    @PersistenceContext
    EntityManager em;

    public void save(BoardImage boardImage) {
        em.persist(boardImage);
    }
}
