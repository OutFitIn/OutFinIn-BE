package com.whatever.ofi.repository;

import com.whatever.ofi.responseDto.CoordinatorMainPageRes;
import com.whatever.ofi.responseDto.UserMainPageRes;
import com.whatever.ofi.responseDto.UserMainTotalRes;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MainPageRepository {
    @PersistenceContext
    EntityManager em;

    public List<CoordinatorMainPageRes> findCoordinatorMainPage() {
        List<Object[]> resultList = em.createQuery(
                        " select c.nickname, c.image_url, c.total_like, c.request_count, c.styles, c.id " +
                                " from Coordinator c " +
                                " group by c.id " +
                                " order by c.request_count desc " , Object[].class)
                .setMaxResults(20)
                .getResultList();

        List<CoordinatorMainPageRes> dtos = new ArrayList<>();

        for (Object[] result : resultList) {
            CoordinatorMainPageRes dto = new CoordinatorMainPageRes();

            dto.setNickname((String) result[0]);
            dto.setProfile_image((String) result[1]);
            dto.setTotal_like((Integer) result[2]);
            dto.setRequest_count((Integer) result[3]);
            dto.setStyles((List<String>) result[4]);
            dto.setCoordinator_id((Long) result[5]);
            dtos.add(dto);
        }

        return dtos;
    }

    public UserMainTotalRes findUserMainPage(Long id) {
        List<Object[]> resultList = em.createQuery(
                        " select c.nickname, c.image_url, b.image_url, c.total_like, c.request_count, c.styles, b.id, c.id" +
                                " from Board b, Coordinator c " +
                                " where b.coordinator.id = c.id " +
                                " order by c.request_count desc " , Object[].class)
                .setMaxResults(20)
                .getResultList();

        List<Long> likes = em.createQuery(
                "select b.board.id from BoardLike b " +
                        "where b.user.id = :id ", Long.class)
                .setParameter("id", id)
                .getResultList();

        Object styles = em.createQuery(
                "select u.styles from User u " +
                        "where u.id = :id ", Object.class)
                .setParameter("id", id)
                .getSingleResult();

        List<UserMainPageRes> dtos = new ArrayList<>();

        for (Object[] result : resultList) {
            UserMainPageRes dto = new UserMainPageRes();

            dto.setNickname((String) result[0]);
            dto.setProfile_image((String) result[1]);
            dto.setBoard_image((String) result[2]);
            dto.setTotal_like((Integer) result[3]);
            dto.setRequest_count((Integer) result[4]);
            dto.setStyles((List<String>) result[5]);
            dto.setBoard_id((Long) result[6]);
            dto.setCoordinator_id((Long) result[7]);
            dtos.add(dto);
        }

        return UserMainTotalRes.builder()
                .pages(dtos)
                .styles((List<String>) styles)
                .user_board_like(likes)
                .build();
    }
}
