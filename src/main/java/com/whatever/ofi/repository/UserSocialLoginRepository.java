package com.whatever.ofi.repository;

import com.whatever.ofi.domain.User;
import com.whatever.ofi.domain.UserSocialLogin;
import com.whatever.ofi.requestDto.TypeId;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserSocialLoginRepository {

    @PersistenceContext
    private EntityManager em;

    public UserSocialLoginRepository findOne(Long user_id) {
        return em.find(UserSocialLoginRepository.class, user_id);
    }

    public void save(UserSocialLogin user) {
        em.persist(user);
    }

    public List<UserSocialLogin> findOneByUserid (User user) {
        List<UserSocialLogin> resultList = em.createQuery(
                        "SELECT u FROM UserSocialLogin u WHERE u.user = :user", UserSocialLogin.class)
                .setParameter("user", user)
                .getResultList();

        return resultList;
    }

    // googleid로 userid, type 찾기
    public TypeId findUserIdByGoogleId(String googleid) {
        List<User> resultList = em.createQuery(
                        "select u.user from UserSocialLogin u " +
                                "where u.googleid = :googleid", User.class)
                .setParameter("googleid", googleid)
                .getResultList();

        System.out.println(resultList.get(0).getId() + " " + "user");

        System.out.println(resultList.get(0).getId() + " " + "user");

        TypeId temp = new TypeId();
        temp.setId(resultList.get(0).getId());
        temp.setType("user");

        return temp;
    }

    // naverid로 userid, type 찾기
    public TypeId findUserIdByNaverId(String naverid) {
        List<User> resultList = em.createQuery(
                        "select u.user from UserSocialLogin u " +
                                "where u.naverid = :naverid", User.class)
                .setParameter("naverid", naverid)
                .getResultList();

        System.out.println(resultList.get(0).getId() + " " + "user");

        TypeId temp = new TypeId();
        temp.setId(resultList.get(0).getId());
        temp.setType("user");

        return temp;
    }

    // kakaoid로 userid, type 찾기
    public TypeId findUserIdByKakaoId(String kakaoid) {
        List<User> resultList = em.createQuery(
                        "select u.user from UserSocialLogin u " +
                                "where u.kakaoid = :kakaoid", User.class)
                .setParameter("kakaoid", kakaoid)
                .getResultList();

        System.out.println(resultList.get(0).getId() + " " + "user");

        TypeId temp = new TypeId();
        temp.setId(resultList.get(0).getId());
        temp.setType("user");

        return temp;
    }

    public void insertKakaoid(String kakaoid, User user) {
        Query query = em.createQuery(
                        "UPDATE UserSocialLogin usl" +
                                " SET usl.kakaoid = :kakaoid"+
                                " WHERE usl.user = :user")
                .setParameter("kakaoid", kakaoid)
                .setParameter("user", user);

        query.executeUpdate();
    }

    public void insertNaverid(String naverid, User user) {
        Query query = em.createQuery(
                        "UPDATE UserSocialLogin usl" +
                                " SET usl.naverid = :naverid"+
                                " WHERE usl.user = :user")
                .setParameter("naverid", naverid)
                .setParameter("user", user);

        query.executeUpdate();
    }

    public void insertGoogleid(String googleid, User user) {
        Query query = em.createQuery(
                        "UPDATE UserSocialLogin usl" +
                                " SET usl.googleid = :googleid"+
                                " WHERE usl.user = :user")
                .setParameter("googleid", googleid)
                .setParameter("user", user);

        query.executeUpdate();
    }
}
