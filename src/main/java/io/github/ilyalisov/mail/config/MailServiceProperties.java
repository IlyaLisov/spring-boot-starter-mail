package io.github.ilyalisov.mail.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Configuration
@Getter
@Setter
@Primary
@ConfigurationProperties(prefix = "spring.mail")
public class MailServiceProperties extends MailProperties {

    /**
     * Vendor of mail service. Could be `mail.ru` or `gmail.com`.
     * If not set, `spring.mail.*` properties are used.
     */
    private String vendor;

    /**
     * List of templates for email.
     */
    private List<MailTemplate> templates;

}
