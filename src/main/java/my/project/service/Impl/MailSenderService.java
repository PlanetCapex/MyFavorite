package my.project.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class MailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    //Метод отправки email cooбщений
    public void send(String emailTo, String subject, String message) {
        //Создание нового сообщения
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //Из переменной окружения получаем используемую почту для отправки сообщений
        mailMessage.setFrom(System.getenv("SPRING_MAIL_USERNAME"));
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
}