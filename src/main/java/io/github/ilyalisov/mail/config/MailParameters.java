package io.github.ilyalisov.mail.config;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Properties;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class MailParameters<T> {

    /**
     * Type of email. It is used to determine the template of the email.
     */
    private T type;

    /**
     * List of email receivers.
     */
    private List<String> receivers;

    /**
     * Text of email. It is used if email is not an HTML template.
     */
    private String text;

    /**
     * Properties for populating template.
     */
    private Properties properties;

    /**
     * Builder with one receiver.
     *
     * @param receiver email of receiver
     * @param type     type of email
     * @param <T>      type of email
     * @return builder
     */
    public static <T> MailParametersBuilder<T> builder(
            final String receiver,
            final T type
    ) {
        return new MailParametersBuilder<T>()
                .properties(new Properties())
                .type(type)
                .receivers(List.of(receiver));
    }

    /**
     * Builder with one receiver.
     *
     * @param receivers emails of receivers
     * @param type      type of email
     * @param <T>       type of email
     * @return builder
     */
    public static <T> MailParametersBuilder<T> builder(
            final List<String> receivers,
            final T type
    ) {
        return new MailParametersBuilder<T>()
                .properties(new Properties())
                .type(type)
                .receivers(receivers);
    }

    public static class MailParametersBuilder<T> {

        /**
         * Setter for property.
         *
         * @param key   key
         * @param value value
         * @return builder
         */
        public MailParametersBuilder<T> property(
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
        public MailParametersBuilder<T> properties(
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
        public MailParametersBuilder<T> text(
                final String text
        ) {
            this.text = text;
            return this;
        }

        /**
         * Actual builder.
         *
         * @return built object
         */
        public MailParameters<T> build() {
            return new MailParameters<>(
                    type,
                    receivers,
                    text,
                    properties
            );
        }

    }

}
