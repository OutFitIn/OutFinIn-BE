package com.whatever.ofi.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoordinatorSocialLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "social_login_id")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coordinator_id")
    private Coordinator coordinator;

    private String kakaoid;
    private String naverid;
    private String googleid;

    @Builder
    public CoordinatorSocialLogin (Coordinator coordinator, String kakaoid, String naverid, String googleid){
        this. coordinator = coordinator;
        this. kakaoid = kakaoid;
        this. naverid = naverid;
        this. googleid = googleid;
    }
}
