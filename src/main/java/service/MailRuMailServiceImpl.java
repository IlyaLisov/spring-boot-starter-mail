package service;

import config.MailTemplateConfig;
import config.vendors.MailRuMailSender;
import freemarker.template.Configuration;

import java.util.Map;

public class MailRuMailServiceImpl<T> extends MailServiceImpl<T> {

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
            final Map<T, MailTemplateConfig> templates
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
