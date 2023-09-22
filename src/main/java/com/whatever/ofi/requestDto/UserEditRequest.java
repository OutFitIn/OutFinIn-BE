package com.whatever.ofi.requestDto;

import com.whatever.ofi.Enum.Gender;
import com.whatever.ofi.Enum.Shape;
import com.whatever.ofi.domain.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UserEditRequest {

    private Long id;

    private String nickname;

    private int height;

    private int weight;

    private Gender gender;

    private Shape shape;

    private List<String> styles = new ArrayList<>();

}
