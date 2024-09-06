package io.github.ilyalisov.mail.service;

import freemarker.template.Configuration;
import io.github.ilyalisov.mail.config.MailParameters;
import io.github.ilyalisov.mail.config.MailTemplate;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

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
    private final Map<String, MailTemplate> templates;

    @Async
    @Override
    @SneakyThrows
    public void send(
            final MailParameters params
    ) {
        MailTemplate template = getTemplate(params);
        String content = getContent(
                params,
                template
        );
        String subject = params.getSubject() != null
                ? params.getSubject()
                : template.getDefaultSubject();
        sendEmail(
                params,
                content,
                template.isHtml(),
                subject
        );
    }

    private MailTemplate getTemplate(
            final MailParameters params
    ) {
        MailTemplate template = templates.get(params.getType());
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
            final MailParameters params,
            final MailTemplate template
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
            final MailParameters params,
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
            final MailParameters params,
            final MailTemplate template
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
        for (Map.Entry<Object, Object> entry : template.getProperties()
                .entrySet()) {
            model.put(
                    String.valueOf(entry.getKey()),
                    String.valueOf(entry.getValue())
            );
        }
        configuration.getTemplate(template.getTemplate())
                .process(model, stringWriter);
        return stringWriter.getBuffer()
                .toString();
    }

}
