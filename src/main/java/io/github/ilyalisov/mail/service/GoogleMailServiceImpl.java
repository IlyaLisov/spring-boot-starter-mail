package io.github.ilyalisov.mail.service;

import freemarker.template.Configuration;
import io.github.ilyalisov.mail.config.MailTemplate;
import io.github.ilyalisov.mail.vendors.GoogleMailSender;

import java.util.Map;

public class GoogleMailServiceImpl extends MailServiceImpl {

    /**
     * Creates an object.
     *
     * @param username      username of gmail.com account
     * @param password      password of gmail.com account
     * @param configuration freemarker configuration
     * @param templates     map of templates
     */
    public GoogleMailServiceImpl(
            final String username,
            final String password,
            final Configuration configuration,
            final Map<String, MailTemplate> templates
    ) {
        super(
                configuration,
                new GoogleMailSender(
                        username,
                        password
                ),
                templates
        );
    }
}
