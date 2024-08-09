package io.github.ilyalisov.mail.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class PlainMailConfiguration implements MailConfiguration {

    /**
     * Subject of email.
     */
    private String subject;

    @Override
    public String getTemplateFileName() {
        return null;
    }

    @Override
    public boolean isHtml() {
        return false;
    }

}
