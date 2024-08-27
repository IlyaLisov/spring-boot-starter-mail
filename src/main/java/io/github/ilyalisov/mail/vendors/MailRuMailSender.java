package io.github.ilyalisov.mail.vendors;

import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class MailRuMailSender extends JavaMailSenderImpl {

    /**
     * Default host of Mail.ru mail server.
     */
    private static final String HOST = "smtp.mail.ru";

    /**
     * Creates and object.
     *
     * @param username username of mail account
     * @param password password of mail account. Often a token for apps
     */
    public MailRuMailSender(
            final String username,
            final String password
    ) {
        Properties props = new Properties();
        props.put("mail.debug", false);
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.ssl.enable", true);
        props.put("mail.smtp.ssl.trust", HOST);
        props.put("mail.smtp.starttls.enable", false);

        this.setHost(HOST);
        this.setPort(465);
        this.setUsername(username);
        this.setPassword(password);
        this.setJavaMailProperties(props);
    }

}
