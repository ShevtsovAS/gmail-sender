package com.example.shevtsov.gmailsender.service;

import com.example.shevtsov.gmailsender.model.SendEmailRequest;
import com.example.shevtsov.gmailsender.model.SendEmailResponse;

public interface EmailService {

    SendEmailResponse sendSimpleMessage(SendEmailRequest request);

    SendEmailResponse sendMimeMessage(SendEmailRequest request);

}
