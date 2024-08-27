package io.github.ilyalisov.mail.config;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Properties;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class MailParameters {

    /**
     * Type of email. It is used to determine the template of the email.
     */
    private String type;

    /**
     * List of email receivers.
     */
    private List<String> receivers;

    /**
     * Text of email. It is used if email is not an HTML template.
     */
    private String text;

    /**
     * Subject of email. If set, it will override the default subject.
     */
    private String subject;

    /**
     * Properties for populating template.
     */
    private Properties properties;

    /**
     * Builder with one receiver.
     *
     * @param receiver email of receiver
     * @param type     type of email
     * @return builder
     */
    public static MailParametersBuilder builder(
            final String receiver,
            final String type
    ) {
        return new MailParametersBuilder()
                .properties(new Properties())
                .type(type)
                .receivers(List.of(receiver));
    }

    /**
     * Builder with one receiver.
     *
     * @param receivers emails of receivers
     * @param type      type of email
     * @return builder
     */
    public static MailParametersBuilder builder(
            final List<String> receivers,
            final String type
    ) {
        return new MailParametersBuilder()
                .properties(new Properties())
                .type(type)
                .receivers(receivers);
    }

    public static class MailParametersBuilder {

        /**
         * Setter for property.
         *
         * @param key   key
         * @param value value
         * @return builder
         */
        public MailParametersBuilder property(
                final String key,
                final String value
        ) {
            this.properties.put(key, value);
            return this;
        }

        /**
         * Setter for properties.
         *
         * @param properties properties
         * @return builder
         */
        public MailParametersBuilder properties(
                final Properties properties
        ) {
            if (this.properties == null) {
                this.properties = new Properties();
            }
            this.properties.putAll(properties);
            return this;
        }

        /**
         * Setter for text.
         *
         * @param text text
         * @return builder
         */
        public MailParametersBuilder text(
                final String text
        ) {
            this.text = text;
            return this;
        }

        /**
         * Setter for subject.
         *
         * @param subject subject
         * @return builder
         */
        public MailParametersBuilder subject(
                final String subject
        ) {
            this.subject = subject;
            return this;
        }

        /**
         * Actual builder.
         *
         * @return built object
         */
        public MailParameters build() {
            return new MailParameters(
                    type,
                    receivers,
                    text,
                    subject,
                    properties
            );
        }

    }

}
