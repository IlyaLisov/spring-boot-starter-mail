package service;

import config.MailTemplateConfig;
import config.vendors.GoogleMailSender;
import freemarker.template.Configuration;

import java.util.Map;

public class GoogleMailServiceImpl<T> extends MailServiceImpl<T> {

    public GoogleMailServiceImpl(
            final String username,
            final String password,
            final Configuration configuration,
            final Map<T, MailTemplateConfig> templates
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
