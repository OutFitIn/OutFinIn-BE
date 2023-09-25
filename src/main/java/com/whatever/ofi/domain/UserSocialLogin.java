package com.whatever.ofi.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSocialLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "social_login_id")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    //@Column(name = "kakaoid", unique = true)
    private String kakaoid;
    //@Column(name = "naverid", unique = true)
    private String naverid;
    //@Column(name = "googleid", unique = true)
    private String googleid;


    @Builder
    public UserSocialLogin (User user, String kakaoid, String naverid, String googleid){
        this. user = user;
        this. kakaoid = kakaoid;
        this. naverid = naverid;
        this. googleid = googleid;
    }
}
