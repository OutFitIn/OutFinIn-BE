package com.whatever.ofi.repository;

import com.whatever.ofi.domain.Coordinator;
import com.whatever.ofi.domain.CoordinatorSocialLogin;
import com.whatever.ofi.domain.User;
import com.whatever.ofi.domain.UserSocialLogin;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

    public List<CoordinatorSocialLogin> findOneByCoordinatorid (Coordinator coordinator) {
        List<CoordinatorSocialLogin> resultList = em.createQuery(
                        "SELECT c FROM CoordinatorSocialLogin c WHERE c.coordinator = :coordinator", CoordinatorSocialLogin.class)
                .setParameter("coordinator", coordinator)
                .getResultList();

        return resultList;
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

        resultMap.put(resultList.get(0).getId(), "coordinator");

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

        resultMap.put(resultList.get(0).getId(), "coordinator");

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

        resultMap.put(resultList.get(0).getId(), "coordinator");

        return resultMap;
    }

    public void insertKakaoid(String kakaoid, Coordinator coordinator) {
        Query query = em.createQuery(
                        "UPDATE CoordinatorSocialLogin c" +
                                " SET c.kakaoid = :kakaoid"+
                                " WHERE c.coordinator = :coordinator")
                .setParameter("kakaoid", kakaoid)
                .setParameter("coordinator", coordinator);

        query.executeUpdate();
    }

    public void insertNaverid(String naverid, Coordinator coordinator) {
        Query query = em.createQuery(
                        "UPDATE CoordinatorSocialLogin c" +
                                " SET c.naverid = :naverid"+
                                " WHERE c.coordinator = :coordinator")
                .setParameter("naverid", naverid)
                .setParameter("coordinator", coordinator);

        query.executeUpdate();
    }

    public void insertGoogleid(String googleid, Coordinator coordinator) {
        Query query = em.createQuery(
                        "UPDATE CoordinatorSocialLogin c" +
                                " SET c.googleid = :googleid"+
                                " WHERE c.coordinator = :coordinator")
                .setParameter("googleid", googleid)
                .setParameter("coordinator", coordinator);

        query.executeUpdate();
    }


}
