package com.example.shevtsov.gmailsender.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendEmailRequest {
    private List<String> sendTo;
    private String sendFrom;
    private String subject;
    private String text;
    private boolean html;
    private List<Attachment> attachments;
    private boolean mimeMessage;
}
