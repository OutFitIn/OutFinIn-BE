package com.whatever.ofi.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_img_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "board_id")
    private Board board;

    private String img_url;

    public void setBoard(Board board) {
        this.board = board;
    }

    public BoardImage(String img_url) {
        this.img_url = img_url;
    }
}
