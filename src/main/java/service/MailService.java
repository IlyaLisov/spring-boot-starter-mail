package service;

import config.MailParameters;

public interface MailService<T> {

    /**
     * Sends an email with subject from template.
     *
     * @param params mail params
     */
    void send(
            MailParameters<T> params
    );

    /**
     * Sends an email with custom subject.
     *
     * @param params  mail params
     * @param subject custom subject for email
     */
    void send(
            MailParameters<T> params,
            String subject
    );

}
