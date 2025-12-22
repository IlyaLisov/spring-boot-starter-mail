package io.github.ilyalisov.mail.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;
import java.util.Map;

/**
 * Configuration properties for custom mail service.
 * Wraps Spring Boot's {@link MailProperties} to provide
 * vendor-specific settings and email templates.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.mail")
public class MailServiceProperties {

    /**
     * Base mail properties from Spring Boot.
     */
    @NestedConfigurationProperty
    private MailProperties base = new MailProperties();

    /**
     * Vendor of mail service, e.g., `mail.ru` or `gmail.com`.
     * If not set, default Spring Boot properties will be used.
     */
    private String vendor;

    /**
     * List of email templates used by the mail service.
     */
    private List<MailTemplate> templates;

    /**
     * Returns the mail server host.
     *
     * @return host of the mail server
     */
    public String getHost() {
        return base.getHost();
    }

    /**
     * Sets the mail server host.
     *
     * @param host host of the mail server
     */
    public void setHost(final String host) {
        base.setHost(host);
    }

    /**
     * Returns the mail server port.
     *
     * @return port of the mail server
     */
    public Integer getPort() {
        return base.getPort();
    }

    /**
     * Sets the mail server port.
     *
     * @param port port of the mail server
     */
    public void setPort(final Integer port) {
        base.setPort(port);
    }

    /**
     * Returns the username for authentication with the mail server.
     *
     * @return mail server username
     */
    public String getUsername() {
        return base.getUsername();
    }

    /**
     * Sets the username for authentication with the mail server.
     *
     * @param username mail server username
     */
    public void setUsername(final String username) {
        base.setUsername(username);
    }

    /**
     * Returns the password for authentication with the mail server.
     *
     * @return mail server password
     */
    public String getPassword() {
        return base.getPassword();
    }

    /**
     * Sets the password for authentication with the mail server.
     *
     * @param password mail server password
     */
    public void setPassword(final String password) {
        base.setPassword(password);
    }

    /**
     * Returns additional JavaMail properties for the mail server.
     *
     * @return map of JavaMail properties
     */
    public Map<String, String> getProperties() {
        return base.getProperties();
    }

}
