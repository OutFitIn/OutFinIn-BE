package com.whatever.ofi.repository;

import com.whatever.ofi.domain.ChatHistory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ChatHistoryRepository {

    @PersistenceContext
    EntityManager em;

    public Long save(ChatHistory chatHistory) {
        em.persist(chatHistory);
        return chatHistory.getId();
    }

    public ChatHistory findById(Long id){
        return em.createQuery("select c from ChatHistory c where c.id =:id ", ChatHistory.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}