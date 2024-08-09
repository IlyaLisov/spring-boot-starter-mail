package config;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Properties;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class MailParameters<T> {

    private T type;
    private List<String> receivers;
    private String text;
    private Properties properties;

    public static <T> MailParametersBuilder<T> builder(
            final String receiver,
            final T type
    ) {
        return new MailParametersBuilder<T>()
                .properties(new Properties())
                .type(type)
                .receiver(receiver);
    }

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

        public MailParametersBuilder<T> property(
                final String key,
                final String value
        ) {
            if (this.properties == null) {
                this.properties = new Properties();
            }
            this.properties.put(key, value);
            return this;
        }

        public MailParametersBuilder<T> properties(
                final Properties properties
        ) {
            if (this.properties == null) {
                this.properties = new Properties();
            }
            this.properties.putAll(properties);
            return this;
        }

        public MailParametersBuilder<T> receiver(
                final String receiver
        ) {
            this.receivers = List.of(receiver);
            return this;
        }

        public MailParametersBuilder<T> text(
                final String text
        ) {
            this.text = text;
            return this;
        }

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
