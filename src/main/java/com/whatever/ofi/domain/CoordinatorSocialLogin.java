package com.whatever.ofi.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoordinatorSocialLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "social_login_id")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coordinator_id")
    private Coordinator coordinator;

    //@Column(name = "kakaoid", unique = true)
    private String kakaoid;
    //@Column(name = "naverid", unique = true)
    private String naverid;
    //@Column(name = "googleid", unique = true)
    private String googleid;


    @Builder
    public CoordinatorSocialLogin (Coordinator coordinator, String kakaoid, String naverid, String googleid){
        this. coordinator = coordinator;
        this. kakaoid = kakaoid;
        this. naverid = naverid;
        this. googleid = googleid;
    }
}
