package com.whatever.ofi.service;


import com.whatever.ofi.OAuth2.GoogleService;
import com.whatever.ofi.OAuth2.KakaoService;
import com.whatever.ofi.OAuth2.NaverService;
import com.whatever.ofi.OAuth2Register.RegisterGoogleService;
import com.whatever.ofi.OAuth2Register.RegisterKakaoService;
import com.whatever.ofi.OAuth2Register.RegisterNaverService;
import com.whatever.ofi.config.Util;
import com.whatever.ofi.domain.Coordinator;
import com.whatever.ofi.domain.CoordinatorSocialLogin;
import com.whatever.ofi.domain.User;
import com.whatever.ofi.domain.UserSocialLogin;
import com.whatever.ofi.repository.CoordinatorRepository;
import com.whatever.ofi.repository.CoordinatorSocialLoginRepository;
import com.whatever.ofi.repository.UserRepository;
import com.whatever.ofi.repository.UserSocialLoginRepository;
import com.whatever.ofi.requestDto.CoordinatorSocialRequest;
import com.whatever.ofi.requestDto.UserSocialRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SocialLoginService {

    @Value("${jwt.secret}")
    private String secretKey;

    private final UserSocialLoginRepository userSocialLoginRepository;
    private final CoordinatorSocialLoginRepository coordinatorSocialLoginRepository;
    private final KakaoService kakaoService;
    private final NaverService naverService;
    private final GoogleService googleService;
    private final RegisterKakaoService registerKakaoService;
    private final RegisterGoogleService registerGoogleService;
    private final RegisterNaverService registerNaverService;
    private final UserRepository userRepository;
    private final CoordinatorRepository coordinatorRepository;

    public void saveUserSns (UserSocialLogin userSocialLogin) {userSocialLoginRepository.save(userSocialLogin);}

    public void saveUserSns (CoordinatorSocialLogin coordinatorSocialLogin) {coordinatorSocialLoginRepository.save(coordinatorSocialLogin);}
    public String getKakaoId (String code) {
        String accessToken = kakaoService.getKaKaoAccessToken(code);
        String kakaoid = kakaoService.createKakaoUser(accessToken);

        return kakaoid;
    }

    public String getNaverId (String code) {
        String accessToken = naverService.getNaverAccessToken(code);
        String naverid = naverService.getUserInfo(accessToken);

        return naverid;
    }

    public String getGoogleId (String code) {
        String accessToken = googleService.getGoogleAccessToken(code);
        String googleid = googleService.getUserInfo(accessToken);

        return googleid;
    }

    public String getRegisterKakaoId (String code) {
        String accessToken = registerKakaoService.getKaKaoAccessToken(code);
        String kakaoid = registerKakaoService.createKakaoUser(accessToken);

        return kakaoid;
    }

    public String getRegisterNaverId (String code) {
        String accessToken = registerNaverService.getNaverAccessToken(code);
        String naverid = registerNaverService.getUserInfo(accessToken);

        return naverid;
    }

    public String getRegisterGoogleId (String code) {
        String accessToken = registerGoogleService.getGoogleAccessToken(code);
        String googleid = registerGoogleService.getUserInfo(accessToken);

        return googleid;
    }

    public Map<Long, String> socialLogin (String social, String socailId) {
        Map<Long, String> CoLogin;
        Map<Long, String> UserLogin;

        if (social.equals("Naver")) {
            CoLogin = coordinatorSocialLoginRepository.findUserIdByNaverId(socailId);
            UserLogin = userSocialLoginRepository.findUserIdByNaverId(socailId);

            if (CoLogin.isEmpty() && UserLogin.isEmpty()) { return CoLogin; }
            else if(CoLogin.isEmpty()) { return UserLogin; }
            else { return CoLogin; }
        }

        else if (social.equals("Kakao")) {
            CoLogin = coordinatorSocialLoginRepository.findUserIdByKakaoId(socailId);
            UserLogin = userSocialLoginRepository.findUserIdByKakaoId(socailId);

            if (CoLogin.isEmpty() && UserLogin.isEmpty()) { return CoLogin; }
            else if(CoLogin.isEmpty()) { return UserLogin; }
            else { return CoLogin; }
        }

        else if (social.equals("Google")) {
            CoLogin = coordinatorSocialLoginRepository.findUserIdByGoogleId(socailId);
            UserLogin = userSocialLoginRepository.findUserIdByGoogleId(socailId);

            if (CoLogin.isEmpty() && UserLogin.isEmpty()) { return CoLogin; }
            else if(CoLogin.isEmpty()) { return UserLogin; }
            else { return CoLogin; }
        }

        else return null;
    }

    public String login(Long id, String type) {
        String nickname = "";
        if (type.equals("coordinator")) {
           nickname = coordinatorRepository.findOne(id).getNickname();
        } else if (type.equals("user")) {
            nickname = userRepository.findOne(id).getNickname();
        }

        return Util.createJwt(type, id, nickname, secretKey);
    }



    public boolean saveKakao(Long id, String type, String kakaoId) {
        if(type.equals("coordinator")) {
            // 유저 or 코디네이터 아이디를 찾고....
            Coordinator coordinator = coordinatorRepository.findOne(id);
            List<CoordinatorSocialLogin> so = coordinatorSocialLoginRepository.findOneByCoordinatorid(coordinator);

            if (so.isEmpty()) {
                CoordinatorSocialRequest co = new CoordinatorSocialRequest();
                co.setCoordinator(coordinator);
                co.setKakaoid(kakaoId);
                coordinatorSocialLoginRepository.save(co.toEntity());
            } else {
                so.get(0).setKakaoid(kakaoId);
                coordinatorSocialLoginRepository.insertKakaoid(kakaoId, coordinator);
            }

            return true;
        } else if (type.equals("user")) {
            User user = userRepository.findOne(id);
            List<UserSocialLogin> so = userSocialLoginRepository.findOneByUserid(user);

            for (UserSocialLogin socialLogin : so) {
                System.out.println(socialLogin); // 또는 socialLogin의 필드를 출력
            }

            System.out.println("카카오)리스트 개수: " + so.size());

            if (so.size() == 0) {
                UserSocialRequest userSocialRequest = new UserSocialRequest();
                userSocialRequest.setUser(user);
                userSocialRequest.setKakaoid(kakaoId);
                userSocialLoginRepository.save(userSocialRequest.toEntity());
            } else {
                so.get(0).setKakaoid(kakaoId);
                userSocialLoginRepository.insertKakaoid(kakaoId, user);
            }

            return true;
        }

        return false;
    }

    public boolean saveNaver(Long id, String type, String naverId) {
        if(type.equals("coordinator")) {
            // 유저 or 코디네이터 아이디를 찾고....
            Coordinator coordinator = coordinatorRepository.findOne(id);
            List<CoordinatorSocialLogin> so = coordinatorSocialLoginRepository.findOneByCoordinatorid(coordinator);

            if (so.isEmpty()) {
                CoordinatorSocialRequest co = new CoordinatorSocialRequest();
                co.setCoordinator(coordinator);
                co.setNaverid(naverId);
                coordinatorSocialLoginRepository.save(co.toEntity());
            } else {
                so.get(0).setNaverid(naverId);
                coordinatorSocialLoginRepository.insertNaverid(naverId, coordinator);
            }

            return true;
        } else if (type.equals("user")) {
            User user = userRepository.findOne(id);
            List<UserSocialLogin> so = userSocialLoginRepository.findOneByUserid(user);

            for (UserSocialLogin socialLogin : so) {
                System.out.println(socialLogin); // 또는 socialLogin의 필드를 출력
            }

            System.out.println("네이버)리스트 개수: " + so.size());

            if (so.isEmpty()) {
                UserSocialRequest userSocialRequest = new UserSocialRequest();
                userSocialRequest.setUser(user);
                userSocialRequest.setNaverid(naverId);
                userSocialLoginRepository.save(userSocialRequest.toEntity());
            } else {
                so.get(0).setNaverid(naverId);
                userSocialLoginRepository.insertNaverid(naverId, user);
            }

            return true;
        }

        return false;
    }

    public boolean saveGoogle(Long id, String type, String googleId) {
        if(type.equals("coordinator")) {
            // 유저 or 코디네이터 아이디를 찾고....
            Coordinator coordinator = coordinatorRepository.findOne(id);
            List<CoordinatorSocialLogin> so = coordinatorSocialLoginRepository.findOneByCoordinatorid(coordinator);

            if (so.isEmpty()) {
                CoordinatorSocialRequest co = new CoordinatorSocialRequest();
                co.setCoordinator(coordinator);
                co.setGoogleid(googleId);
                coordinatorSocialLoginRepository.save(co.toEntity());
            } else {
                so.get(0).setGoogleid(googleId);
                coordinatorSocialLoginRepository.insertGoogleid(googleId, coordinator);
            }

            return true;
        } else if (type.equals("user")) {
            User user = userRepository.findOne(id);
            List<UserSocialLogin> so = userSocialLoginRepository.findOneByUserid(user);

            if (so.isEmpty()) {
                UserSocialRequest userSocialRequest = new UserSocialRequest();
                userSocialRequest.setUser(user);
                userSocialRequest.setGoogleid(googleId);
                userSocialLoginRepository.save(userSocialRequest.toEntity());
            } else {
                so.get(0).setGoogleid(googleId);
                userSocialLoginRepository.insertGoogleid(googleId, user);
            }

            return true;
        }


        return false;
    }
}
