package io.github.ilyalisov.mail.service;

import freemarker.template.Configuration;
import io.github.ilyalisov.mail.config.MailTemplate;
import io.github.ilyalisov.mail.vendors.MailRuMailSender;

import java.util.Map;

public class MailRuMailServiceImpl extends MailServiceImpl {

    /**
     * Creates an object.
     *
     * @param username      username of mail.ru account
     * @param password      password of mail.ru account
     * @param configuration freemarker configuration
     * @param templates     map of templates
     */
    public MailRuMailServiceImpl(
            final String username,
            final String password,
            final Configuration configuration,
            final Map<String, MailTemplate> templates
    ) {
        super(
                configuration,
                new MailRuMailSender(
                        username,
                        password
                ),
                templates
        );
    }
}
