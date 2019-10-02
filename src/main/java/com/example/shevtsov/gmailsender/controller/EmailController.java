package com.example.shevtsov.gmailsender.controller;

import com.example.shevtsov.gmailsender.model.SendEmailRequest;
import com.example.shevtsov.gmailsender.model.SendEmailResponse;
import com.example.shevtsov.gmailsender.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<SendEmailResponse> sendEmail(@RequestBody SendEmailRequest request) {
        SendEmailResponse sendEmailResponse = request.isMimeMessage()
                ? emailService.sendMimeMessage(request)
                : emailService.sendSimpleMessage(request);

        return sendEmailResponse.isSuccess()
                ? ResponseEntity.ok(sendEmailResponse)
                : ResponseEntity.badRequest().body(sendEmailResponse);
    }
}
