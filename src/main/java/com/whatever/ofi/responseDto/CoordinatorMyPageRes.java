package com.whatever.ofi.responseDto;

import com.whatever.ofi.Enum.Gender;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CoordinatorMyPageRes {
    private String nickname;

    private String sns_url;

    private String image_url;

    private String content;


    private int height;

    private int weight;

    private int total_like;

    private int request_count;

    private List<String> styles = new ArrayList<>();

    @Builder
    public CoordinatorMyPageRes(String nickname, String sns_url, String image_url, String content,
                                int height, int weight, int total_like, int request_count, List<String> styles){
        this.nickname = nickname;
        this.sns_url = sns_url;
        this.image_url = image_url;
        this.content = content;
        this.height = height;
        this.weight = weight;
        this.total_like = total_like;
        this.request_count = request_count;
        this.styles.addAll(styles);
    }
}
