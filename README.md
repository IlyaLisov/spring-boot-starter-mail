# Spring Boot Starter Mail

[![Lines-of-Code](https://tokei.rs/b1/github/ilyalisov/spring-boot-starter-mail)](https://github.com/ilyalisov/jwt)
[![Hits-of-Code](https://hitsofcode.com/github/ilyalisov/spring-boot-starter-mail?branch=master)](https://hitsofcode.com/github/ilyalisov/jwt/view?branch=master)
[![mvn](https://github.com/ilyalisov/spring-boot-starter-mail/actions/workflows/maven-build.yml/badge.svg)](https://github.com/ilyalisov/jwt/actions/workflows/maven-build.yml)

[![codecov](https://codecov.io/gh/IlyaLisov/spring-boot-starter-mail/graph/badge.svg?token=OJR6TFQ2qr)](https://codecov.io/gh/IlyaLisov/jwt)

This repository is an open-source Java library for fast and convenient using of
MailSender in your Spring Boot applications.

## Content:

* [How to use](#how-to-use)
    * [Prerequisites](#prerequisites)
    * [Instantiate a service](#instantiate-a-service)
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
    <version>0.2.1</version>
</dependency>
```

This library provides simple and convenient usage.

### Prerequisites

You need to use some strings to define email types. We suggest to use enums.
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

This library provide starter for `MailService` interface. You can use it in your
Spring Boot application and configure via `application.properties` or
`application.yml` file.

```yaml
spring:
  mail:
    username: mailaccount@gmail.com
    password: somepasword
    vendor: gmail.com
    templates:
      - type: REGISTRATION
        defaultSubject: "Thanks for registration!"
        template: "registration.ftlh"
        properties:
          somePropertyInTemplate: someValueOfProperty
      - type: PASSWORD_RESET
        defaultSubject: "Password reset"
        template: "reset.ftlh"
      - type: NEWSLETTER
        defaultSubject: "Newsletter"
```

You can use `vendor` property to define mail vendor. This property is optional.

Now we support 2 mail vendors:

* `gmail.com` - Google Mail
* `mail.ru` - Mail.Ru Mail

If you do not provide it, service will use default implementation and
provided `spring.mail` properties.

You can use `templates` property to define email templates. You need to provide
`type` property to define email type. This is required property.

You can use `defaultSubject` property to define default subject of email. This
is required property. Default subject can be overridden in `MailParameters`
builder.

You can use `template` property to define template file. This is optional
property. If you do not provide it, no template will be used and email would be
just plain text.

After startup, service will load all templates from `templates` directory and
store them in memory. You can use `MailService` bean in your services.

### Send email

`MailService` is using `@Async` annotation to send emails. This will make your
application faster. Average time of sending email is about 5-10 second.

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
    public void sendEmail() {
        mailService.send(
                MailParameters.builder(
                                "bob@example.com",
                                MailType.REGISTRATION.toString()
                        )
                        .property("name", "Bob")
                        .build()
        );
    }
}
```

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
    public void sendEmail() {
        mailService.send(
                MailParameters.builder(
                                "bob@example.com",
                                MailType.REGISTRATION.toString()
                        )
                        .subject("Custom Subject")
                        .property("name", "Bob")
                        .build()
        );
    }
}
```

### Send email to many people

In this case service will iterate over all provided emails and send email to
each of them. It is convenient to send same emails to many people.

```java

@Service
@RequiredArgsConstructor
public class SomeService {

    /**
     * Bean of MailService.
     */
    private final MailService mailService;

    /**
     * This method sends newsletter email to bob@example.com
     * and alice@example.com.
     */
    public void sendEmail() {
        mailService.send(
                MailParameters.builder(
                                List.of(
                                        "bob@example.com",
                                        "alice@example.com"
                                ),
                                MailType.NEWSLETTER.toString()
                        )
                        .build()
        );
    }
}
```

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
     * This method sends newsletter email to bob@example.com
     * and alice@example.com.
     */
    public void sendEmail() {
        mailService.send(
                MailParameters.builder(
                                List.of(
                                        "bob@example.com",
                                        "alice@example.com"
                                ),
                                MailType.NEWSLETTER.toString()
                        )
                        .subject("Custom Subject")
                        .build()
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
