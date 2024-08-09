# Spring Boot Starter Mail

[![Lines-of-Code](https://tokei.rs/b1/github/ilyalisov/spring-boot-starter-mail)](https://github.com/ilyalisov/spring-boot-starter-mail)
[![Hits-of-Code](https://hitsofcode.com/github/ilyalisov/spring-boot-starter-mail?branch=master)](https://hitsofcode.com/github/ilyalisov/spring-boot-starter-mail/view?branch=master)
[![mvn](https://github.com/ilyalisov/spring-boot-starter-mail/actions/workflows/maven-build.yml/badge.svg)](https://github.com/ilyalisov/spring-boot-starter-mail/actions/workflows/maven-build.yml)

[![codecov](https://codecov.io/gh/IlyaLisov/spring-boot-starter-mail/graph/badge.svg?token=OJR6TFQ2qr)](https://codecov.io/gh/IlyaLisov/spring-boot-starter-mail)

This repository is an open-source Java library for fast and convenient using of
MailSender in your Spring Boot applications.

## Content:

* [How to use](#how-to-use)
    * [Prerequisites](#prerequisites)
    * [Instantiate a service](#instantiate-a-service)
        * [MailServiceImpl](#mailserviceimpl)
        * [GoogleMailService](#googlemailservice)
        * [MailRuMailService](#mailrumailservice)
    * [Send email](#send-email)
    * [Send email to many people](#send-email-to-many-people)
    * [Templates](#templates)
* [How to contribute](#how-to-contribute)

## How to use

At first, you need to install this library.

With Maven add dependency to your `pom.xml`.

```xml

<dependency>
    <groupId>io.github.ilyalisov</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
    <version>0.1.0</version>
</dependency>
```

This library provides simple and convenient usage.

### Prerequisites

You need to use some Objects to define email types. We suggest to use enums.
This is concise and clear way to define email types.

```java
public enum EmailType {
    REGISTRATION,
    PASSWORD_RESET,
    NEWSLETTER
}
```

You need to have `src/main/resources/templates` directory in your project. This
is default directory for email templates.

### Instantiate a service

We have 3 implementations of `MailService` interface. We recommend to use it as
beans and do not instantiate it by itself.

We work on implementing more convenient ways to use this library.

#### MailServiceImpl

This is default implementation, where you need to provide your own mail server
settings.

You need to have `application.properties` or `application.yml` file in your

```java

@Configuration
@RequiredArgsConstructor
public class MailConfig {

    /**
     * This is a FreeMarker configuration.
     */
    private final Configuration configuration;

    /**
     * This is a JavaMailSender.
     */
    private final JavaMailSender javaMailSender;

    /**
     * This bean creates a map of mail templates for each type.
     *
     * REGISTRATION mail template is an HTML template, has subject and its
     * freemarker template is located
     * in src/main/resources/templates/registration.ftlh
     *
     * PASSWORD_RESET mail template is not an HTML template, has subject and
     * service will use provided text as email body
     *
     * NEWSLETTER mail template is an HTML template, has subject and its
     * freemarker template is located
     * in src/main/resources/templates/news.ftlh
     *
     * @return map of templates
     */
    @Bean
    public Map<MailType, MailConfiguration> templates() {
        Map<MailType, MailConfiguration> templates = new HashMap<>();
        templates.put(
                MailType.REGISTRATION,
                new TemplateMailConfiguration(
                        "registration.ftlh",
                        "Registration"
                )
        );
        templates.put(
                MailType.PASSWORD_RESET,
                new PlainMailConfiguration(
                        "Password Reset"
                )
        );
        templates.put(
                MailType.NEWSLETTER,
                new TemplateMailConfiguration(
                        "news.ftlh",
                        "Newsletter"
                )
        );
        return templates;
    }

    @Bean
    public MailService defaultMailService() {
        Map<MailType, MailConfiguration> templates = templates();
        return new MailServiceImpl(
                configuration,
                javaMailSender,
                templates
        );
    }
}
```

#### GoogleMailService

For some mail vendors we have predefined implementations. For example, for
Google Mail we have `GoogleMailService`.

```java

@Configuration
@RequiredArgsConstructor
public class GoogleMailConfig {

    /**
     * This is a FreeMarker configuration.
     */
    private final Configuration configuration;

    /**
     * This is a mail account username.
     */
    @Value("${spring.mail.username}")
    private String username;

    /**
     * This is a mail account token.
     */
    @Value("${spring.mail.password}")
    private String password;

    /**
     * This bean creates a map of mail templates for each type.
     *
     * REGISTRATION mail template is an HTML template, has subject and its
     * freemarker template is located
     * in src/main/resources/templates/registration.ftlh
     *
     * PASSWORD_RESET mail template is not an HTML template, has subject and
     * service will use provided text as email body
     *
     * NEWSLETTER mail template is an HTML template, has subject and its
     * freemarker template is located
     * in src/main/resources/templates/news.ftlh
     *
     * @return map of templates
     */
    @Bean
    public Map<MailType, MailConfiguration> templates() {
        Map<MailType, MailConfiguration> templates = new HashMap<>();
        templates.put(
                MailType.REGISTRATION,
                new TemplateMailConfiguration(
                        "registration.ftlh",
                        "Registration"
                )
        );
        templates.put(
                MailType.PASSWORD_RESET,
                new PlainMailConfiguration(
                        "Password Reset"
                )
        );
        templates.put(
                MailType.NEWSLETTER,
                new TemplateMailConfiguration(
                        "news.ftlh",
                        "Newsletter"
                )
        );
        return templates;
    }

    @Bean
    public MailService googleMailService() {
        Map<MailType, MailConfiguration> templates = templates();
        return new GoogleMailServiceImpl(
                username,
                password,
                configuration,
                templates
        );
    }
}
```

#### MailRuMailService

For some mail vendors we have predefined implementations. For example, for
MailRu Mail we have `MailRuMailService`.

```java

@Configuration
@RequiredArgsConstructor
public class GoogleMailConfig {

    /**
     * This is a FreeMarker configuration.
     */
    private final Configuration configuration;

    /**
     * This is a mail account username.
     */
    @Value("${spring.mail.username}")
    private String username;

    /**
     * This is a mail account token.
     */
    @Value("${spring.mail.password}")
    private String password;

    /**
     * This bean creates a map of mail templates for each type.
     *
     * REGISTRATION mail template is an HTML template, has subject and its
     * freemarker template is located
     * in src/main/resources/templates/registration.ftlh
     *
     * PASSWORD_RESET mail template is not an HTML template, has subject and
     * service will use provided text as email body
     *
     * NEWSLETTER mail template is an HTML template, has subject and its
     * freemarker template is located
     * in src/main/resources/templates/news.ftlh
     *
     * @return map of templates
     */
    @Bean
    public Map<MailType, MailConfiguration> templates() {
        Map<MailType, MailConfiguration> templates = new HashMap<>();
        templates.put(
                MailType.REGISTRATION,
                new TemplateMailConfiguration(
                        "registration.ftlh",
                        "Registration"
                )
        );
        templates.put(
                MailType.PASSWORD_RESET,
                new PlainMailConfiguration(
                        "Password Reset"
                )
        );
        templates.put(
                MailType.NEWSLETTER,
                new TemplateMailConfiguration(
                        "news.ftlh",
                        "Newsletter"
                )
        );
        return templates;
    }

    @Bean
    public MailService mailRuMailService() {
        Map<MailType, MailConfiguration> templates = templates();
        return new MailRuMailServiceImpl(
                username,
                password,
                configuration,
                templates
        );
    }
}
```

### Send email

When you define your `MailService` bean, you can use it in your services.

```java

@Service
@RequiredArgsConstructor
public class SomeService {

    /**
     * Bean of MailService.
     */
    private final MailService mailService;

    /**
     * This method sends registration email to example@example.com.
     * Service will use provided name Bob and email template defined before.
     */
    @Async
    public void sendEmail() {
        mailService.send(
                MailParameters.builder(
                                "bob@example.com",
                                MailType.REGISTRATION
                        )
                        .property("name", "Bob")
                        .build()
        );
    }
}
```

We recommend to use `@Async` annotation on your service methods, that send
emails. This will make your application faster. Average time of sending email
is about 5-10 second.

You can also provide custom subject.

```java

@Service
@RequiredArgsConstructor
public class SomeService {

    /**
     * Bean of MailService.
     */
    private final MailService mailService;

    /**
     * This method sends registration email to example@example.com with subject
     * Custom Subject.
     * Service will use provided name Bob and email template defined before.
     */
    @Async
    public void sendEmail() {
        mailService.send(
                MailParameters.builder(
                                "bob@example.com",
                                MailType.REGISTRATION
                        )
                        .property("name", "Bob")
                        .build(),
                "Custom subject"
        );
    }
}
```

### Send email to many people

When you define your `MailService` bean, you can use it in your services.

```java

@Service
@RequiredArgsConstructor
public class SomeService {

    /**
     * Bean of MailService.
     */
    private final MailService mailService;

    /**
     * This method sends newsletter email to bob@example.com and alice@example.com.
     */
    @Async
    public void sendEmail() {
        mailService.send(
                MailParameters.builder(
                                List.of(
                                        "bob@example.com",
                                        "alice@example.com"
                                ),
                                MailType.NEWSLETTER
                        )
                        .build()
        );
    }
}
```

In this case service will iterate over all provided emails and send email to
each of them. It is convenient to send same emails to many people.

We recommend to use `@Async` annotation on your service methods, that send
emails. This will make your application faster. Average time of sending email
is about 5-10 second.

You can also provide custom subject.

```java

@Service
@RequiredArgsConstructor
public class SomeService {

    /**
     * Bean of MailService.
     */
    private final MailService mailService;

    /**
     * This method sends newsletter email to bob@example.com and alice@example.com.
     */
    @Async
    public void sendEmail() {
        mailService.send(
                MailParameters.builder(
                                List.of(
                                        "bob@example.com",
                                        "alice@example.com"
                                ),
                                MailType.NEWSLETTER
                        )
                        .build(),
                "Custom subject"
        );
    }
}
```

### Templates

You need to have `src/main/resources/templates` directory in your project. This
is default directory for email templates.

This library uses FreeMarker for email templates. So you can read more about
FreeMarker in [official documentation](https://freemarker.apache.org/docs/).

Let's take a look at example of email template `notification.ftlh`.

```html
<#ftl encoding="UTF-8">
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Notification</title>
    <style>
        #body {
            display: flex;
            flex-direction: column;
            font-family: "Segoe UI", serif;
            align-items: center;
            background-color: #ecedef;
            font-size: 20px;
            height: 100%;
        }

        #div {
            display: block;
            flex-direction: column;
            margin: 10%;
            font-family: "Segoe UI", serif;
            background-color: white;
            padding: 3%;
            border-radius: 20px;
            color: black;
        }

        #button {
            background-color: #ecedef;
            border-radius: 10px;
            padding: 5px;
            color: white;
            text-decoration: none;
        }

        .a {
            text-decoration: none;
        }

        #notification {
            border-style: solid;
            border-color: black;
            border-width: 1px;
            border-radius: 10px;
            display: block;
            flex-direction: column;
            padding-left: 20px;
            padding-right: 20px;
            padding-bottom: 20px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<div id="body">
    <div id="div">
        <h1 style="text-align: center">Notification</h1>
        <p>Hi, ${name}!</p>
        <p>You receiver a notification.</p>
        <div id="notification">
            <h3>${title?no_esc}</h3>
            <div>${text?no_esc}</div>
        </div>
        <a id="button" href="${buttonOpenText}">Open</a>
    </div>
</div>
</body>
</html>
```

This template has some variables:

* `name` - name of the user
* `title` - title of the notification
* `text` - text of the notification. We use `?no_esc` to prevent escaping of
  HTML tags, so you can use HTML tags in your email body
* `buttonOpenText` - text of the button

## How to contribute

See active issues
at [issues page](https://github.com/ilyalisov/spring-boot-starter-mail/issues)
