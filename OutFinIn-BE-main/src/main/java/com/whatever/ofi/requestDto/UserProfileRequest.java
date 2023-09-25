package com.whatever.ofi.requestDto;

import com.whatever.ofi.Enum.Gender;
import com.whatever.ofi.Enum.Shape;
import com.whatever.ofi.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.catalina.LifecycleState;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class UserProfileRequest {
    private String email;

    private String password;

    private String nickname;

    private int height;

    private int weight;

    private Gender gender;

    private Shape shape;

    private List<String> styles = new ArrayList<>();

    public User toEntity() {
        return User.builder()
                .styles(styles)
                .email(email)
                .password(password)
                .gender(gender)
                .height(height)
                .weight(weight)
                .nickname(nickname)
                .shape(shape)
                .build();
    }
}
