package config.vendors;

import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class GoogleMailSender extends JavaMailSenderImpl {

    private static final String HOST = "smtp.gmail.com";

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
