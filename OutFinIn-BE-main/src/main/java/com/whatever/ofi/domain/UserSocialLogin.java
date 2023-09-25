package com.whatever.ofi.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSocialLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "social_login_id")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private String kakaoid;
    private String naverid;
    private String googleid;

    @Builder
    public UserSocialLogin (User user, String kakaoid, String naverid, String googleid){
        this. user = user;
        this. kakaoid = kakaoid;
        this. naverid = naverid;
        this. googleid = googleid;
    }
}
