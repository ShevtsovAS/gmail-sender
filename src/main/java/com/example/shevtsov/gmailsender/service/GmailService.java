package com.example.shevtsov.gmailsender.service;

import com.example.shevtsov.gmailsender.model.Attachment;
import com.example.shevtsov.gmailsender.model.SendEmailRequest;
import com.example.shevtsov.gmailsender.model.SendEmailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class GmailService implements EmailService {

    private final JavaMailSender javaMailSender;

    @Override
    public SendEmailResponse sendSimpleMessage(SendEmailRequest request) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(request.getSendFrom());
        msg.setTo(request.getSendTo().toArray(new String[0]));
        msg.setSubject(request.getSubject());
        msg.setText(request.getText());

        try {
            javaMailSender.send(msg);
            return getSuccessfulEmailResponse(request);
        } catch (MailException e) {
            log.error(e.getLocalizedMessage(), e);
            return getErrorEmailResponse(request, e);
        }
    }

    @Override
    public SendEmailResponse sendMimeMessage(SendEmailRequest request) {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setFrom(request.getSendFrom());
            helper.setTo(request.getSendTo().toArray(new String[0]));
            helper.setSubject(request.getSubject());
            helper.setText(request.getText(), request.isHtml());

            if (!CollectionUtils.isEmpty(request.getAttachments())) {
                for (Attachment attachment : request.getAttachments()) {
                    helper.addAttachment(attachment.getAttachmentFileName(), new FileSystemResource(attachment.getAttachmentFilePath()));
                }
            }

            javaMailSender.send(msg);

            return getSuccessfulEmailResponse(request);

        } catch (MessagingException e) {
            log.error(e.getLocalizedMessage(), e);
            return getErrorEmailResponse(request, e);
        }
    }

    private SendEmailResponse getSuccessfulEmailResponse(SendEmailRequest request) {
        return SendEmailResponse.builder()
                .success(true)
                .dateTime(LocalDateTime.now())
                .message(String.format("Письмо было успешно отправленно на %s.", String.join(", ", request.getSendTo())))
                .build();
    }

    private SendEmailResponse getErrorEmailResponse(SendEmailRequest request, Exception e) {
        return SendEmailResponse.builder()
                .dateTime(LocalDateTime.now())
                .message(String.format("Ошибка отправления письма на %s", request.getSendTo()))
                .errorMessage(e.getLocalizedMessage())
                .build();
    }
}
