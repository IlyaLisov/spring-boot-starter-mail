package service;

import config.MailParameters;

public interface MailService<T> {

    void send(
            MailParameters<T> params
    );

    void send(
            MailParameters<T> params,
            String subject
    );

}
