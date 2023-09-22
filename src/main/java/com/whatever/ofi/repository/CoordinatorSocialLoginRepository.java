package com.whatever.ofi.repository;

import com.whatever.ofi.domain.Coordinator;
import com.whatever.ofi.domain.CoordinatorSocialLogin;
import com.whatever.ofi.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CoordinatorSocialLoginRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(CoordinatorSocialLogin user) {
        em.persist(user);
    }

    // googleid로 userid, type 찾기
    public Map<Long, String> findUserIdByGoogleId(String googleid) {
        List<Coordinator> resultList = em.createQuery(
                        "select c.coordinator from CoordinatorSocialLogin c " +
                                "where c.googleid = :googleid", Coordinator.class)
                .setParameter("googleid", googleid)
                .getResultList();

        Map<Long, String> resultMap = new HashMap<>();
        if(resultList.isEmpty()) return resultMap;

        resultMap.put(resultList.get(0).getId(), "Coordinator");

        return resultMap;
    }

    // naverid로 userid, type 찾기
    public Map<Long, String> findUserIdByNaverId(String naverid) {
        List<Coordinator> resultList = em.createQuery(
                        "select c.coordinator from CoordinatorSocialLogin c " +
                                "where c.naverid = :naverid", Coordinator.class)
                .setParameter("naverid", naverid)
                .getResultList();

        Map<Long, String> resultMap = new HashMap<>();
        if(resultList.isEmpty()) return resultMap;

        resultMap.put(resultList.get(0).getId(), "Coordinator");

        return resultMap;
    }

    // kakaoid로 userid, type 찾기
    public Map<Long, String> findUserIdByKakaoId(String kakaoid) {
        List<Coordinator> resultList = em.createQuery(
                        "select c.coordinator from CoordinatorSocialLogin c " +
                                "where c.kakaoid = :kakaoid", Coordinator.class)
                .setParameter("kakaoid", kakaoid)
                .getResultList();

        Map<Long, String> resultMap = new HashMap<>();
        if(resultList.isEmpty()) return resultMap;

        resultMap.put(resultList.get(0).getId(), "Coordinator");

        return resultMap;
    }


}
