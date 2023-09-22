package com.whatever.ofi.repository;

import com.whatever.ofi.domain.Board;
import com.whatever.ofi.domain.Coordinator;
import com.whatever.ofi.requestDto.SearchRequest;
import com.whatever.ofi.responseDto.BoardInfo;
import com.whatever.ofi.responseDto.CoordinatorWithBoard;
import com.whatever.ofi.responseDto.CoordinatorWithSearch;
import com.whatever.ofi.responseDto.UserMainPageRes;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class SearchRepository {
    @PersistenceContext
    EntityManager em;

    public List<UserMainPageRes> findByStyles(){
        List<Object[]> resultList = em.createQuery(
                        " select c.nickname, c.image_url, b.image_url, c.total_like, c.request_count, c.styles " +
                                " from Board b, Coordinator c " +
                                " where b.coordinator.id = c.id " +
                                " group by c.id " +
                                " order by rand() " , Object[].class)
                .setMaxResults(20)
                .getResultList();

        List<UserMainPageRes> dtos = new ArrayList<>();

        for (Object[] result : resultList) {
            UserMainPageRes dto = new UserMainPageRes();

            dto.setNickname((String) result[0]);
            dto.setProfile_image((String) result[1]);
            dto.setBoard_image((String) result[2]);
            dto.setTotal_like((Integer) result[3]);
            dto.setRequest_count((Integer) result[4]);
            dto.setStyles((List<String>) result[5]);
            dtos.add(dto);
        }

        return dtos;
    }

    public List<CoordinatorWithBoard> findCoordinatorWithBoard(SearchRequest searchRequest) {
        // 기존 코드

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createQuery(Tuple.class);
        Root<Coordinator> coordinatorRoot = query.from(Coordinator.class);

        Join<Coordinator, Board> boardJoin = coordinatorRoot.join("boards", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();

        if (searchRequest.getStyles() != null && !searchRequest.getStyles().isEmpty()) {
            Predicate stylePredicate = boardJoin.get("style").in(searchRequest.getStyles());
            predicates.add(stylePredicate);
        }

        if (searchRequest.getSeason() != null && !searchRequest.getSeason().isEmpty()) {
            Predicate seasonPredicate = boardJoin.get("season").in(searchRequest.getSeason());
            predicates.add(seasonPredicate);
        }

        if (searchRequest.getSituation() != null && !searchRequest.getSituation().isEmpty()) {
            Predicate situationPredicate = boardJoin.get("situation").in(searchRequest.getSituation());
            predicates.add(situationPredicate);
        }

        if (!predicates.isEmpty()) {
            Predicate orPredicate = builder.or(predicates.toArray(new Predicate[0]));
            query.where(orPredicate);
        }

        query.multiselect(
                coordinatorRoot.get("nickname").alias("coornickname"),
                coordinatorRoot.get("image_url").alias("coorimageUrl"),
                coordinatorRoot.get("request_count").alias("coorrequestCount"),
                coordinatorRoot.get("styles").alias("coorstyles"),
                boardJoin.get("season").alias("boardseason"),
                boardJoin.get("situation").alias("boardsituation"),
                boardJoin.get("image_url").alias("boardImage"),
                boardJoin.get("like_count").alias("boardlikeCount"),
                boardJoin.get("style").alias("boardStyle")
        );

        List<Tuple> results = em.createQuery(query).getResultList();

        // 중복 제거 코드

        // Set을 사용하여 중복 제거
        Set<CoordinatorWithBoard> coordinatorMainPageResSet = new HashSet<>();

        // 결과를 Set에 추가
        for (Tuple tuple : results) {
            CoordinatorWithBoard dto = new CoordinatorWithBoard();
            dto.setBoardImage(tuple.get("boardImage", String.class));
            dto.setBoardStyle(tuple.get("boardStyle", String.class));
            dto.setCoornickname(tuple.get("coornickname", String.class));
            dto.setBoardlikeCount(tuple.get("boardlikeCount", Integer.class));
            dto.setCoorimageUrl(tuple.get("coorimageUrl", String.class));
            dto.setCoorrequestCount(tuple.get("coorrequestCount", Integer.class));

            // 처리할 데이터 형식에 따라 다르게 설정
            dto.setBoardseason(tuple.get("boardseason", String.class));
            dto.setBoardsituation(tuple.get("boardsituation", String.class));

            // coorstyles가 문자열 배열로 반환될 경우
            // dto.setCoorstyles(tuple.get("coorstyles", String[].class));

            coordinatorMainPageResSet.add(dto);
        }

        List<CoordinatorWithBoard> coordinatorMainPageResList = new ArrayList<>(coordinatorMainPageResSet);

        return coordinatorMainPageResList;
    }



    public List<CoordinatorWithSearch> findLikeRating(){
        List<CoordinatorWithSearch> dtos = new ArrayList<>();

        // Coordinator 정보를 가져옴
        List<Object[]> coordinatorResults = em.createQuery(
                        "SELECT c.id, c.nickname, c.image_url, c.total_like, c.request_count, c.styles " +
                                "FROM Coordinator c " +
                                "ORDER BY c.total_like DESC", Object[].class)
                .setMaxResults(20)
                .getResultList();

        for (Object[] coordinatorResult : coordinatorResults) {
            CoordinatorWithSearch coordinatorDto = new CoordinatorWithSearch();

            coordinatorDto.setCoornickname((String) coordinatorResult[1]);
            coordinatorDto.setCoorimageUrl((String) coordinatorResult[2]);
            coordinatorDto.setCoorTotalLike((Integer) coordinatorResult[3]);
            coordinatorDto.setCoorrequestCount((Integer) coordinatorResult[4]);
            coordinatorDto.setCoorstyles((List<String>) coordinatorResult[5]);

            // Coordinator에 해당하는 Board 정보를 가져옴
            List<Object[]> boardResults = em.createQuery(
                            "SELECT b.image_url, b.like_count, b.content " +
                                    "FROM Board b " +
                                    "WHERE b.coordinator.id = :coordinatorId", Object[].class)
                    .setParameter("coordinatorId", (Long) coordinatorResult[0])
                    .getResultList();

            List<BoardInfo> boardInfos = new ArrayList<>();
            for (Object[] boardResult : boardResults) {
                BoardInfo boardInfo = new BoardInfo();
                boardInfo.setBoardImage((String) boardResult[0]);
                boardInfo.setBoardlikeCount((Integer) boardResult[1]);
                boardInfo.setBoardcontent((String) boardResult[2]);
                boardInfos.add(boardInfo);
            }

            coordinatorDto.setBoardInfos(boardInfos);
            dtos.add(coordinatorDto);
        }

        return dtos;
    }

    public List<CoordinatorWithSearch> findCountRating() {
        List<CoordinatorWithSearch> dtos = new ArrayList<>();

        // Coordinator 정보를 가져옴
        List<Object[]> coordinatorResults = em.createQuery(
                        "SELECT c.id, c.nickname, c.image_url, c.total_like, c.request_count, c.styles " +
                                "FROM Coordinator c " +
                                "ORDER BY c.request_count DESC", Object[].class)
                .setMaxResults(20)
                .getResultList();

        for (Object[] coordinatorResult : coordinatorResults) {
            CoordinatorWithSearch coordinatorDto = new CoordinatorWithSearch();

            coordinatorDto.setCoornickname((String) coordinatorResult[1]);
            coordinatorDto.setCoorimageUrl((String) coordinatorResult[2]);
            coordinatorDto.setCoorTotalLike((Integer) coordinatorResult[3]);
            coordinatorDto.setCoorrequestCount((Integer) coordinatorResult[4]);
            coordinatorDto.setCoorstyles((List<String>) coordinatorResult[5]);

            // Coordinator에 해당하는 Board 정보를 가져옴
            List<Object[]> boardResults = em.createQuery(
                            "SELECT b.image_url, b.like_count, b.content " +
                                    "FROM Board b " +
                                    "WHERE b.coordinator.id = :coordinatorId", Object[].class)
                    .setParameter("coordinatorId", (Long) coordinatorResult[0])
                    .getResultList();

            List<BoardInfo> boardInfos = new ArrayList<>();
            for (Object[] boardResult : boardResults) {
                BoardInfo boardInfo = new BoardInfo();
                boardInfo.setBoardImage((String) boardResult[0]);
                boardInfo.setBoardlikeCount((Integer) boardResult[1]);
                boardInfo.setBoardcontent((String) boardResult[2]);
                boardInfos.add(boardInfo);
            }

            coordinatorDto.setBoardInfos(boardInfos);
            dtos.add(coordinatorDto);
        }

        return dtos;
    }
}
