package com.whatever.ofi.repository;

import com.whatever.ofi.Enum.Gender;
import com.whatever.ofi.domain.Board;
import com.whatever.ofi.domain.Coordinator;
import com.whatever.ofi.domain.User;
import com.whatever.ofi.responseDto.CoordinatorAllBoardRes;
import com.whatever.ofi.responseDto.CoordinatorInfoRes;
import com.whatever.ofi.responseDto.CoordinatorMyPageRes;
import com.whatever.ofi.responseDto.UserMainPageRes;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CoordinatorRepository {
    @PersistenceContext
    EntityManager em;

    public void save(Coordinator coordinator) {
        em.persist(coordinator);
    }

    public Coordinator findOne(Long id) {
        return em.find(Coordinator.class, id);
    }

    public Long findId(String email) {
        return em.createQuery(
                "select c.id from Coordinator c " +
                        "where c.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public List<String> findByEmail(String email) {
        return em.createQuery("select c.email from Coordinator c where c.email = :email ", String.class)
                .setParameter("email", email)
                .getResultList();
    }

    public Coordinator findByPassword(String email) {
        return em.createQuery("select c from Coordinator c where c.email = :email", Coordinator.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public CoordinatorMyPageRes findMyPage(Long id) {
        Object[] result = em.createQuery(
                "select c.nickname, c.sns_url, c.image_url, c.content, " +
                        " c.height, c.weight, c.total_like, c.request_count, c.styles " +
                        " from Coordinator c " +
                        " where c.id = :id", Object[].class)
                .setParameter("id", id)
                .getSingleResult();

        return CoordinatorMyPageRes.builder()
                .nickname((String) result[0])
                .sns_url((String) result[1])
                .image_url((String) result[2])
                .content((String) result[3])
                .height((Integer) result[4])
                .weight((Integer) result[5])
                .total_like((Integer) result[6])
                .request_count((Integer) result[7])
                .styles((List<String>) result[8])
                .build();
    }

    public List<CoordinatorAllBoardRes> findAllBoard(Long id) {
        List<Object[]> resultList = em.createQuery(
                "select b.id, b.title, b.image_url from Board b " +
                        "where b.coordinator.id = :id ", Object[].class)
                .setParameter("id", id)
                .getResultList();

        List<CoordinatorAllBoardRes> response = new ArrayList<>();

        for(Object[] result : resultList) {
            response.add(new CoordinatorAllBoardRes(
                    (Long) result[0], (String) result[1], (String) result[2]));
        }

        return response;
    }

    public CoordinatorInfoRes findInfo(Long id) {
        Object[] result =  em.createQuery(
                " select c.nickname, c.sns_url, c.image_url, c.content, c.gender, c.height, c.weight,c.styles " +
                        " from Coordinator c " +
                        " where c.id = :id", Object[].class)
                .setParameter("id", id)
                .getSingleResult();

        return CoordinatorInfoRes.builder()
                .nickname((String) result[0])
                .sns_url((String) result[1])
                .image_url((String) result[2])
                .content((String) result[3])
                .gender((Gender) result[4])
                .height((Integer) result[5])
                .weight((Integer) result[6])
                .styles((List<String>) result[7])
                .build();
    }

    public Coordinator findByNickName(String nickname){
        return em.createQuery(" select c from Coordinator c where c.nickname = :nickname ", Coordinator.class)
                .setParameter("nickname", nickname)
                .getSingleResult();
    }
}
