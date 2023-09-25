package com.whatever.ofi.repository;

import com.whatever.ofi.Enum.Gender;
import com.whatever.ofi.Enum.Shape;
import com.whatever.ofi.domain.User;
import com.whatever.ofi.responseDto.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
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
                        "select u.nickname, u.height, u.weight, u.shape, u.styles, u.gender " +
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
                .gender((Gender) result[5])
                .build();
    }

    public UserInfoRes findInfo(Long id) {
        Object[] result =  em.createQuery(
                        " select u.nickname, u.gender, u.height, u.weight, u.shape, u.styles " +
                                " from User u " +
                                " where u.id = :id", Object[].class)
                .setParameter("id", id)
                .getSingleResult();

        return UserInfoRes.builder()
                .nickname((String) result[0])
                .gender((Gender) result[1])
                .height((Integer) result[2])
                .weight((Integer) result[3])
                .shape((Shape) result[4])
                .styles((List<String>) result[5])
                .build();
    }

    public List<Long> findBoardLikeById(Long id) {
        return em.createQuery(
                        "select b.board.id from BoardLike b " +
                                "where b.user.id = :id ", Long.class)
                .setParameter("id", id)
                .getResultList();
    }

    public User findByNickName(String nickname){
        return em.createQuery(" select u from User u where u.nickname = :nickname ", User.class)
                .setParameter("nickname", nickname)
                .getSingleResult();
    }

    public List<User> findNickname(String nickname) {
        return em.createQuery(" select u from User u where u.nickname = :nickname ", User.class)
                .setParameter("nickname", nickname)
                .getResultList();
    }
}
