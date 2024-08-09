package config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class MailTemplateConfigImpl implements MailTemplateConfig {

    private String template;
    private String subject;
    private boolean isHtml;

    public MailTemplateConfigImpl(
            final String subject
    ) {
        this.subject = subject;
    }

}
