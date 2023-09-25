package com.whatever.ofi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
@PropertySource("classpath:application.yml")
public class MailService {

    private final JavaMailSender emailSender;

    public static String ePw = "";

    private MimeMessage createMessage(String to) throws Exception{
        System.out.println("보내는 대상 : " + to);
        System.out.println("인증 번호 : " + ePw);
        // https://seumu-s3-bucket.s3.ap-northeast-2.amazonaws.com/banner_pattern.svg
        // https://drive.google.com/uc?export=view&amp;id=1Sq-EX7P31_gwDoSmUh5SJDD-8z1dfhKq

        String msgg =
                "<div style=\"width: 1280px; overflow: hidden;\">\n" +
                        "   <div style=\"width: 1280px;\">\n" +
                        "       <img src=\"https://seumu-s3-bucket.s3.ap-northeast-2.amazonaws.com/banner_pattern.svg\"/ width=\"1280px;\">\n" +
                        "   </div>\n" +
                        "   <div style=\"margin: 93px 0px 0px 160px; width: 100%;\">\n" +
                        "       <div style=\"color: #000; font-size: 34px; font-weight: 700; line-height: normal; letter-spacing: 0.085px; width: 700px;\">\n" +
                        "            OFI 인증메일입니다\n" +
                        "       </div>\n" +
                        "       <div style=\"margin-top: 46px; color: #000; font-size: 16px; font-weight: 400; line-height: normal; letter-spacing: 0.04px; width: 100%;\">\n" +
                        "           안녕하세요 OFI 인증메일입니다.<br>\n" +
                        "           5분 이내에 이메일에 있는 인증코드를 입력해 주세요.\n" +
                        "       </div>\n" +
                        "       <div style=\"margin-top: 46px; color: #000; font-size: 36px; font-weight: 700; line-height: normal; text-decoration: underline; width: 700px;\">\n" +
                        "            인증 코드: "+ePw+"\n" +
                        "       </div>\n" +
                        "       <div style=\"margin-top: 46px; color: #000; font-size: 16px; font-weight: 400; line-height: normal; letter-spacing: 0.04px; width: 100%;\">\n" +
                        "           인증번호를 인증창에 직접 입력하시거나 인증키를 복사 후 회원가입 페이지의 입력창에 붙여넣기 해주세요.<br>\n" +
                        "           타인에게 인증번호 유출을 주의해주세요.\n" +
                        "       </div>\n" +
                        "   </div>\n" +
                        "   <div style=\"margin-top: 40px; bottom: 0; width: 1280px; height: 300px%; background: #F3EFF4; padding: 50px 614px 50px 164px; color:#929094; font-size: 12px; font-weight: 400; line-height: normal; letter-spacing: 0.048px;\"> " +
                        "   본 메일은 발신전용이며, 문의에 대한 회신은 처리되지 않습니다.<br> " +
                        "   (주)아웃핏인 | 주소: 충남 천안시 동남구 상명대로 & 서울특별시 종로구 홍지문2길 20 | 팀원: 김규민 박준형 안지수 오현식 이소윤 이수정<br><br> " +
                        "   Copyright ©OFI Corp. All rights reserved. " +
                        "   </div> " +
                        "</div>";

        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to);//보내는 대상
        message.setSubject("[OutFitIn] 회원가입 이메일 인증번호");//제목

        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("ofi1234@naver.com","OutFitIn"));

        return message;
    }

    public static String createKey(int length) {
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        Set<Integer> usedNumbers = new HashSet<>();

        while (code.length() < length) {
            SimpleDateFormat format1 = new SimpleDateFormat ( "ss");
            Date time = new Date();
            int time1 = Integer.parseInt(format1.format(time));

            int randomNumber = random.nextInt(10) * time1;// 0부터 9 사이의 랜덤 숫자 생성

            if (randomNumber >= 10) {
                randomNumber = randomNumber % (randomNumber / 10);
            }


            // 생성된 숫자가 중복되지 않도록 확인
            if (!usedNumbers.contains(randomNumber)) {
                code.append(randomNumber);
                usedNumbers.add(randomNumber);
            }
        }

        return code.toString();
    }



    public String sendSimpleMessage(String to)throws Exception {
        ePw= createKey(4);

        // TODO Auto-generated method stub
        MimeMessage message = createMessage(to);
        try{//예외처리
            emailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return ePw;
    }

}
