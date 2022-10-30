package com.banzo.reddit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
class MailContentBuilder {

  private final TemplateEngine templateEngine;

  public String build(String message) {
    Context context = new Context();
    context.setVariable("message", message);
    return templateEngine.process("mailTemplate", context);
  }

}
