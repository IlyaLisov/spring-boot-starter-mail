package io.github.ilyalisov.mail.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class TemplateMailConfiguration implements MailConfiguration {

    /**
     * Subject of email.
     */
    private String subject;

    /**
     * Template file name.
     */
    private String templateFileName;

    @Override
    public boolean isHtml() {
        return true;
    }

}