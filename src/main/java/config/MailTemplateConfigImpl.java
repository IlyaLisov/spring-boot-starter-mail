package config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class MailTemplateConfigImpl implements MailTemplateConfig {

    /**
     * Template file name.
     */
    private String templateFileName;

    /**
     * Subject of email.
     */
    private String subject;

    /**
     * True if email is in HTML format.
     */
    private boolean isHtml;

    /**
     * Creates an object.
     *
     * @param subject subject of email
     */
    public MailTemplateConfigImpl(
            final String subject
    ) {
        this.subject = subject;
    }

}
