package io.github.ilyalisov.mail.service;

import io.github.ilyalisov.mail.config.MailConfig;
import io.github.ilyalisov.mail.config.vendors.GoogleMailSender;
import freemarker.template.Configuration;

import java.util.Map;

public class GoogleMailServiceImpl<T> extends MailServiceImpl<T> {

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
            final Map<T, MailConfig> templates
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
