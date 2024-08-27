package io.github.ilyalisov.mail.service;

import io.github.ilyalisov.mail.config.MailParameters;

public interface MailService {

    /**
     * Sends an email with subject from template.
     *
     * @param params mail params
     */
    void send(
            MailParameters params
    );

    /**
     * Sends an email with custom subject.
     *
     * @param params  mail params
     * @param subject custom subject for email
     */
    void send(
            MailParameters params,
            String subject
    );

}
