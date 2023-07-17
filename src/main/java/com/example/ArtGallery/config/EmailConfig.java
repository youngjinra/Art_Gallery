package com.example.ArtGallery.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {
    @Value("${spring.mail.username}")   // properties에 작성된 이메일 id 값
    private String loginID;

    @Value("${spring.mail.password}")   // properties에 작성된 이메일 password 값
    private String loginPWD;

    @Bean
    public JavaMailSender javaMailService(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost("smtp.naver.com");
        javaMailSender.setUsername(loginID);    // 네이버 아이디
        javaMailSender.setPassword(loginPWD);   // 네이버 비밀번호
        javaMailSender.setPort(465);            // 메일 인증서버 포트

        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }

    private Properties getMailProperties(){
        Properties properties = new Properties();                       // 메일 인증서버 가져오기
        properties.setProperty("mail.transport.protocol", "smtp");      // 프로토콜 설정
        properties.setProperty("mail.smtp.auth", "true");               // smtp 인증
        properties.setProperty("mail.smtp.starttls.enable", "true");    // smtp strattles 사용
        properties.setProperty("mail.debug", "true");                   // 디버그 사용
        properties.setProperty("mail.smtp.ssl.trust","smtp.naver.com"); // ssl 인증 서버는 smtp.naver.com
        properties.setProperty("mail.smtp.ssl.enable","true");          // ssl 사용
        return properties;
    }
}
