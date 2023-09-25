package com.whatever.ofi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.kafka.common.protocol.types.Field;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coordinator_id")
    private Coordinator coordinator;

    @JsonIgnore
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BoardImage> urls = new ArrayList<>();

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<BoardLike> boardLikes = new ArrayList<>();

    private String style;

    private int like_count;

    private String season;

    private String situation;

    private String content;

    private String image_url;

    private String title;

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    //==연관관계 메서드==//
    public void addUrl(BoardImage boardImg) {
        urls.add(boardImg);
        boardImg.setBoard(this);
    }

    public void addBoardLike(BoardLike boardLike) {
        this.boardLikes.add(boardLike);
    }

    //==비지니스 로직==//
    public void addLike() {
        this.like_count += 1;
        this.getCoordinator().setTotal_like(1);
    }

    public String removeLike() {
        if(this.like_count - 1 < 0) {
            return "impossible";
        }

        this.like_count -= 1;
        this.getCoordinator().setTotal_like(-1);
        return "possible";
    }

    @Builder
    public Board(String style, int like_count, String season,
                 String situation, String content, String image_url, String title){
        this.style = style;
        this.like_count = like_count;
        this.season = season;
        this.situation = situation;
        this.content = content;
        this.image_url = image_url;
        this.title = title;
    }
}
