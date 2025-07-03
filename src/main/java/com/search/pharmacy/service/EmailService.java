package com.search.pharmacy.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailService {

  private final JavaMailSender javaMailSender;
  private final TemplateEngine templateEngine;

  @Value("${spring.mail.properties.mail.smtp.from}")
  private String sender;

  public void sendEmail(String to, String subject, String templateName, Context context)
      throws MessagingException {
    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

    helper.setFrom(sender);
    helper.setTo(to);
    helper.setSubject(subject);

    String htmlContent = templateEngine.process(templateName, context);
    helper.setText(htmlContent, true);

    javaMailSender.send(message);
  }
}
