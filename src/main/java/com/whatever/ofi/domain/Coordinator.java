package com.whatever.ofi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whatever.ofi.Enum.Gender;
import com.whatever.ofi.config.StringListConverter;
import com.whatever.ofi.requestDto.CoordinatorEditRequest;
import com.whatever.ofi.requestDto.UserEditRequest;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString(exclude = "password")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coordinator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="coordinator_id")
    private Long id;

    private String email;

    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "coordinator", cascade = CascadeType.ALL)
    private List<Board> boards = new ArrayList<>();

    @JsonIgnore
    @Convert(converter = StringListConverter.class)
    private List<String> styles = new ArrayList<>();

    private String nickname;

    private String sns_url;

    private String image_url;

    private String content;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int height;

    private int weight;

    private int total_like;

    private int request_count;

    public void setTotal_like(int count) {
        this.total_like += count;
    }

    //==연관관계 메서드==//
    public void addBoard(Board board) {
        boards.add(board);
        board.setCoordinator(this);
    }

    public void edit(CoordinatorEditRequest dto) {
        this.nickname = dto.getNickname();
        this.height = dto.getHeight();
        this.weight = dto.getWeight();
        this.gender = dto.getGender();
        this.sns_url = dto.getSns_url();
        this.content = dto.getContent();
        this.image_url = dto.getImage_url();
        this.styles = dto.getStyles();
    }

    @Builder
    public Coordinator(String email, String password, String nickname, String sns_url, String image_url, String content,
                              Gender gender, int height, int weight, int total_like, int request_count, List<String> styles){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.sns_url = sns_url;
        this.image_url = image_url;
        this.content = content;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.total_like = total_like;
        this.request_count = request_count;
        this.styles.addAll(styles);
    }
}
