package com.aicheck.business.domain.auth.application.service;

import com.aicheck.business.domain.auth.dto.CheckCodeDto;
import com.aicheck.business.domain.auth.exception.BusinessException;
import com.aicheck.business.domain.auth.infrastructure.AuthCodeRepository;
import com.aicheck.business.global.error.BusinessErrorCodes;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private String FROM_MAIL;
    private static final String MAIL_SEND_EXCEPTION_MESSAGE = "메일 전송 중 오류";

    private final JavaMailSender mailSender;
    private final AuthCodeRepository authCodeRepository;


    public void sendAuthenticationCode(String subject, String email) {
        String toMail = email;
        String randomCode = generateRandomCode();
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "utf-8");
            messageHelper.setFrom(FROM_MAIL);
            messageHelper.setTo(toMail);
            messageHelper.setSubject(subject);
            messageHelper.setText(randomCode);

            mailSender.send(message);
            authCodeRepository.save(email, randomCode);
        } catch (MailSendException e) {
            throw new BusinessException(BusinessErrorCodes.MAIL_EXCEPTION);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void checkCode(CheckCodeDto checkCodeDTO) {
        String storedCode = authCodeRepository.findCodeByEmailAndRandomCode(checkCodeDTO);
        authCodeRepository.delete(checkCodeDTO.getEmail());
        if (storedCode == null || !checkCodeDTO.getCode().equals(storedCode.toString())) {
            throw new BusinessException(BusinessErrorCodes.WRONG_MAIL_CODE);
        }
    }

    private String generateRandomCode() {
        Random random = new Random();
        int n = random.nextInt(1000000);
        return String.format("%06d", n);
    }


}
