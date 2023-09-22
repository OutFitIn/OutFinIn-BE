package com.whatever.ofi.repository;

import com.whatever.ofi.Enum.Gender;
import com.whatever.ofi.Enum.Shape;
import com.whatever.ofi.domain.User;
import com.whatever.ofi.responseDto.CoordinatorMyPageRes;
import com.whatever.ofi.responseDto.UserMyPageRes;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    public Long findId(String email) {
        return em.createQuery(
                        "select u.id from User u " +
                                "where u.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public List<String> findByEmail(String email) {
        return em.createQuery("select u.email from User u where u.email = :email ", String.class)
                .setParameter("email", email)
                .getResultList();
    }

    public User findByPassword(String email) {
        return em.createQuery("select u from User u where u.email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public UserMyPageRes findMyPage(Long id) {
        Object[] result = em.createQuery(
                        "select u.nickname, u.height, u.weight, u.shape, u.styles " +
                                " from User u " +
                                " where u.id = :id", Object[].class)
                .setParameter("id", id)
                .getSingleResult();

        return UserMyPageRes.builder()
                .nickname((String) result[0])
                .height((Integer) result[1])
                .weight((Integer) result[2])
                .shape((Shape) result[3])
                .styles((List<String>) result[4])
                .build();
    }
}
