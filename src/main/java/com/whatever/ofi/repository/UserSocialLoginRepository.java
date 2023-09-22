package com.whatever.ofi.repository;

import com.whatever.ofi.domain.User;
import com.whatever.ofi.domain.UserSocialLogin;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserSocialLoginRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(UserSocialLogin user) {
        em.persist(user);
    }

    // googleid로 userid, type 찾기
    public Map<Long, String> findUserIdByGoogleId(String googleid) {
        List<User> resultList = em.createQuery(
                        "select u.user from UserSocialLogin u " +
                                "where u.googleid = :googleid", User.class)
                .setParameter("googleid", googleid)
                .getResultList();

        Map<Long, String> resultMap = new HashMap<>();
        if(resultList.isEmpty()) return resultMap;

        resultMap.put(resultList.get(0).getId(), "User");

        return resultMap;
    }

    // naverid로 userid, type 찾기
    public Map<Long, String> findUserIdByNaverId(String naverid) {
        List<User> resultList = em.createQuery(
                        "select u.user from UserSocialLogin u " +
                                "where u.naverid = :naverid", User.class)
                .setParameter("naverid", naverid)
                .getResultList();

        Map<Long, String> resultMap = new HashMap<>();
        if(resultList.isEmpty()) return resultMap;

        resultMap.put(resultList.get(0).getId(), "User");

        return resultMap;
    }

    // kakaoid로 userid, type 찾기
    public Map<Long, String> findUserIdByKakaoId(String kakaoid) {
        List<User> resultList = em.createQuery(
                        "select u.user from UserSocialLogin u " +
                                "where u.kakaoid = :kakaoid", User.class)
                .setParameter("kakaoid", kakaoid)
                .getResultList();

        Map<Long, String> resultMap = new HashMap<>();
        if(resultList.isEmpty()) return resultMap;

        resultMap.put(resultList.get(0).getId(), "User");

        return resultMap;
    }
}
