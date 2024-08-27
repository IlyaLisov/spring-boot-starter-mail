package io.github.ilyalisov.mail.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Properties;

@Getter
@Setter
@NoArgsConstructor
public class MailTemplate {

    /**
     * Type of email.
     */
    private String type;

    /**
     * Default subject of email.
     */
    private String defaultSubject;

    /**
     * Template for email. If null, email is plain.
     */
    private String template;

    /**
     *  Predefined properties for each email of type.
     */
    private Properties properties;

    /**
     * Creates a plain email template.
     *
     * @param type           type of email
     * @param defaultSubject default subject of email
     */
    public MailTemplate(
            final String type,
            final String defaultSubject
    ) {
        this.type = type;
        this.defaultSubject = defaultSubject;
        this.template = null;
    }

    /**
     * Creates an email template.
     *
     * @param type           type of email
     * @param defaultSubject default subject of email
     * @param template       name of template file
     */
    public MailTemplate(
            final String type,
            final String defaultSubject,
            final String template
    ) {
        this.type = type;
        this.defaultSubject = defaultSubject;
        this.template = template;
    }

    /**
     * Checks if email is HTML.
     *
     * @return true if email is template
     */
    public boolean isHtml() {
        return template != null;
    }

}
