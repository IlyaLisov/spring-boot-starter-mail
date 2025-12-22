package io.github.ilyalisov.mail;

import io.github.ilyalisov.mail.config.MailServiceProperties;
import io.github.ilyalisov.mail.config.MailTemplate;
import io.github.ilyalisov.mail.service.GoogleMailServiceImpl;
import io.github.ilyalisov.mail.service.MailRuMailServiceImpl;
import io.github.ilyalisov.mail.service.MailService;
import io.github.ilyalisov.mail.service.MailServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(MailServiceProperties.class)
@Slf4j
public class MailAutoConfiguration {

    /**
     * MailServiceProperties for configuring MailService.
     */
    private final MailServiceProperties mailProperties;

    /**
     * Freemarker configuration.
     */
    private final freemarker.template.Configuration freemarkerConfiguration;

    /**
     * Creates a mail service bean.
     *
     * @return mail service
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean
    public MailService mailService() {
        Map<String, MailTemplate> templates = new HashMap<>();
        if (mailProperties.getTemplates() != null) {
            for (MailTemplate template : mailProperties.getTemplates()) {
                templates.put(template.getType(), template);
            }
        }
        String vendor = mailProperties.getVendor() != null
                ? mailProperties.getVendor().toLowerCase()
                : "";
        switch (vendor) {
            case "gmail.com":
                log.info("Using Gmail.com mail sender.");
                return new GoogleMailServiceImpl(
                        mailProperties.getUsername(),
                        mailProperties.getPassword(),
                        freemarkerConfiguration,
                        templates
                );
            case "mail.ru":
                log.info("Using Mail.ru mail sender.");
                return new MailRuMailServiceImpl(
                        mailProperties.getUsername(),
                        mailProperties.getPassword(),
                        freemarkerConfiguration,
                        templates
                );
            default:
                log.warn("Mail vendor is not specified. "
                        + "Using default mail sender.");
                JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
                mailSender.setHost(mailProperties.getHost());
                mailSender.setPort(mailProperties.getPort());
                mailSender.setUsername(mailProperties.getUsername());
                mailSender.setPassword(mailProperties.getPassword());
                Properties properties = new Properties();
                properties.putAll(mailProperties.getProperties());
                mailSender.setJavaMailProperties(properties);
                return new MailServiceImpl(
                        freemarkerConfiguration,
                        mailSender,
                        templates
                );
        }
    }

}
