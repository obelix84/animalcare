package ru.animalcare.service.imp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.animalcare.service.EmailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    public JavaMailSender javaMailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    //Оптравка через javaMailSender
    public void sendEmail(String receiver,String OTP) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        String senderAddress = "our email";
        mailMessage.setFrom(senderAddress);
        mailMessage.setTo(receiver);
        mailMessage.setText(defaultMessage(receiver,OTP));
        javaMailSender.send(mailMessage);
    }

    //Возможно не будем использовать, пока не знаю.
    @Override
    public void sendEmailWithAttachments(String receiver, String message, List<Object> attachments) throws MessagingException {
        MimeMessage msg = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(receiver);

        helper.setSubject("Testing from Spring Boot");

        // default = text/plain
        //helper.setText("Check attachment for image!");

        // true = text/html
        helper.setText("<h1>Check attachment for image!</h1>", true);

        //Optional
        //helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));

        javaMailSender.send(msg);

    }

    public String defaultMessage(String receiver, String OTP) {
        String greeting = "Здравствуйте, " + receiver + "!\n\n\n";
        String body = "Подтвердите вашу учетную запись! Введите код ниже на сайте animalcare.ru чтобы подтвердить учетную запись\n\n\n";
        String otp = "Ваш одноразовый код- " + OTP;
        String ending = "\n\n\nМы рады, что вы выбрали нас и надеемся, что вы найдете питомца\n\n\n\n\n\n\n";
        String footer = "Пожалуйста, не отвечайте на это сообщение. Письма, отправленные на эту почту, не проверяются";
        String message = greeting + body + otp + ending + footer;
        return message;
    }
}
