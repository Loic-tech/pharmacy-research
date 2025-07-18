package com.search.pharmacy.service;

import com.search.pharmacy.domain.model.User;
import jakarta.mail.MessagingException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileValidationService {

  private final EmailService emailService;

  public void sendValidationProfileEmail(User user) {
    try {
      Context context = new Context();
      context.setVariable("firstname", user.getFirstName());
      context.setVariable("lastname", user.getLastName());
      context.setVariable(
          "validationDate",
          LocalDateTime.now().getDayOfMonth()
              + "/"
              + LocalDateTime.now().getMonthValue()
              + "/"
              + LocalDateTime.now().getYear());

      emailService.sendEmail(user.getEmail(), "Profil Validé", "validation-profile", context);
    } catch (MessagingException e) {
      log.error("Failed to send validation profile email to {} ", user.getEmail());
      throw new RuntimeException(e);
    }
  }
}
