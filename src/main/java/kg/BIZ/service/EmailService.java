package kg.BIZ.service;

import kg.BIZ.dto.response.SimpleResponse;

public interface EmailService {
    SimpleResponse sendEmail(String to, String subject, String text);
}
