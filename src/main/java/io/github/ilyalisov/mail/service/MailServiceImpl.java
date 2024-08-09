package io.github.ilyalisov.mail.service;

import io.github.ilyalisov.mail.config.MailParameters;
import io.github.ilyalisov.mail.config.MailConfig;
import freemarker.template.Configuration;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class MailServiceImpl<T> implements MailService<T> {

    /**
     * Freemarker configuration.
     */
    private final Configuration configuration;

    /**
     * JavaMailSender object.
     */
    private final JavaMailSenderImpl sender;

    /**
     * Map of templates.
     */
    private final Map<T, MailConfig> templates;

    @Override
    @SneakyThrows
    public void send(
            final MailParameters<T> params
    ) {
        MailConfig template = getTemplate(params);
        String content = getContent(
                params,
                template
        );
        sendEmail(
                params,
                content,
                template.isHtml(),
                template.getSubject()
        );
    }

    @Override
    @SneakyThrows
    public void send(
            final MailParameters<T> params,
            final String subject
    ) {
        MailConfig template = getTemplate(params);
        String content = getContent(
                params,
                template
        );
        sendEmail(
                params,
                content,
                template.isHtml(),
                subject
        );
    }

    private MailConfig getTemplate(
            final MailParameters<T> params
    ) {
        MailConfig template = templates.get(params.getType());
        if (template == null) {
            throw new IllegalStateException(
                    "No template found for type: " + params.getType()
            );
        }
        if (!template.isHtml() && params.getText() == null) {
            throw new IllegalStateException(
                    "No text provided for plain type: " + params.getType()
            );
        }
        return template;
    }

    private String getContent(
            final MailParameters<T> params,
            final MailConfig template
    ) {
        String content;
        if (template.isHtml()) {
            content = processTemplate(
                    params,
                    template
            );
        } else {
            content = params.getText();
        }
        return content;
    }

    private void sendEmail(
            final MailParameters<T> params,
            final String content,
            final boolean isHtml,
            final String subject
    ) {
        for (String receiver : params.getReceivers()) {
            try {
                MimeMessage mimeMessage = sender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(
                        mimeMessage,
                        false,
                        "UTF-8"
                );
                helper.setSubject(subject);
                helper.setTo(receiver);
                helper.setFrom(sender.getUsername());
                helper.setText(content, isHtml);
                sender.send(mimeMessage);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @SneakyThrows
    private String processTemplate(
            final MailParameters<T> params,
            final MailConfig template
    ) {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        for (Map.Entry<Object, Object> entry : params.getProperties()
                .entrySet()) {
            model.put(
                    String.valueOf(entry.getKey()),
                    String.valueOf(entry.getValue())
            );
        }
        configuration.getTemplate(template.getTemplateFileName())
                .process(model, stringWriter);
        return stringWriter.getBuffer()
                .toString();
    }

}
