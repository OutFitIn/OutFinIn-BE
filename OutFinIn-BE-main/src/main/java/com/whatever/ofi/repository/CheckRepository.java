package com.whatever.ofi.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CheckRepository {
    @PersistenceContext
    EntityManager em;
    public List<String> findByNickname(String nickname) {
        List<String> nicknames = em.createQuery("select c.nickname from Coordinator c where c.nickname = :nickname", String.class)
                .setParameter("nickname", nickname)
                .getResultList();

        nicknames.addAll(em.createQuery("select u.nickname from User u where u.nickname = :nickname", String.class)
                .setParameter("nickname", nickname)
                .getResultList());

        return nicknames;
    }

    public List<String> findByEmail(String email) {
        List<String> emails = em.createQuery("select c.email from Coordinator c where c.email = :email", String.class)
                .setParameter("email", email)
                .getResultList();

        emails.addAll(em.createQuery("select u.email from User u where u.email = :email", String.class)
                .setParameter("email", email)
                .getResultList());

        return emails;
    }
}
