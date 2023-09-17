package kg.BIZ.service.impl;

import kg.BIZ.dto.response.SimpleResponse;
import kg.BIZ.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    @Override
    public SimpleResponse sendEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            javaMailSender.send(message);
            return new SimpleResponse(HttpStatus.OK,"Письмо отправлено успешно.");
        }catch (Exception e){
            return new SimpleResponse(HttpStatus.BAD_REQUEST,"Ошибка отправки письма: " + e.getMessage());
        }
    }
}
