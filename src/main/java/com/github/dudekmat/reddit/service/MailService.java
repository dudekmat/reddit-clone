package com.github.dudekmat.reddit.service;

import com.github.dudekmat.reddit.exception.NotificationException;
import com.github.dudekmat.reddit.model.NotificationEmail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
class MailService {

  private static final String SENDER_EMAIL = "reddit@dudekmat.github.com";

  private final JavaMailSender mailSender;

  @Async
  public void sendMail(NotificationEmail notificationEmail) {
    MimeMessagePreparator preparedMail = prepareMail(notificationEmail);

    try {
      mailSender.send(preparedMail);
      log.info("Activation email sent!");
    } catch (MailException e) {
      throw new NotificationException(
          "Error occurred when sending mail to " + notificationEmail.getRecipient());
    }
  }

  private MimeMessagePreparator prepareMail(NotificationEmail notificationEmail) {
    return mimeMessage -> {
      MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
      messageHelper.setFrom(SENDER_EMAIL);
      messageHelper.setTo(notificationEmail.getRecipient());
      messageHelper.setSubject(notificationEmail.getSubject());
      messageHelper.setText(notificationEmail.getBody());
    };
  }

}
