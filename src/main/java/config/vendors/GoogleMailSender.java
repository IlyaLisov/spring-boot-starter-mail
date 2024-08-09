package config.vendors;

import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class GoogleMailSender extends JavaMailSenderImpl {

    /**
     * Default host of Gmail mail server.
     */
    private static final String HOST = "smtp.gmail.com";

    /**
     * Creates and object.
     *
     * @param username username of mail account
     * @param password password of mail account. Often a token for apps
     */
    public GoogleMailSender(
            final String username,
            final String password
    ) {
        Properties props = new Properties();
        props.put("mail.debug", false);
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);

        this.setHost(HOST);
        this.setPort(587);
        this.setUsername(username);
        this.setPassword(password);
        this.setJavaMailProperties(props);
    }

}
