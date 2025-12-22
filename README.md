# Spring Boot Starter Mail

[![mvn](https://github.com/ilyalisov/spring-boot-starter-mail/actions/workflows/maven-build.yml/badge.svg)](https://github.com/ilyalisov/spring-boot-starter-mail/actions/workflows/maven-build.yml)
[![codecov](https://codecov.io/gh/IlyaLisov/spring-boot-starter-mail/graph/badge.svg?token=OJR6TFQ2qr)](https://codecov.io/gh/IlyaLisov/spring-boot-starter-mail)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.1-brightgreen.svg)](https://spring.io/projects/spring-boot)

An open-source library for fast and convenient email sending.

## Table of contents:

* [Features](#features)
* [Quick Start](#quick-start)
* [Installation](#installation)
    * [Maven](#maven)
    * [Gradle](#gradle)
* [Core components](#core-components)
    * [Mail Type](#mail-type)
    * [MailTemplate](#mail-template)
    * [MailService](#mail-service)
* [Usage Examples](#usage-examples)
* [License](#license)
* [Contributing](#contributing)

## Features

- **Pre-built MailService** - Mail service that instantiated just at startup
- **Mail Templates** - Configurable mail templates that allows to use different
  templates for each mail type

## Quick Start

This library provides all the essential components you need for emailing.

## Installation

### Maven

Add to your `pom.xml`:

```xml

<dependency>
    <groupId>io.github.ilyalisov</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
    <version>0.3.0</version>
</dependency>
```

### Gradle

Add to your `build.gradle` or `build.gradle.kts`:

```groovy
implementation("io.github.ilyalisov:spring-boot-starter-mail:0.3.0")
```

## Core Components

### Mail Type

To start using this library, you need to use some strings to define email types.
We suggest to use enums.
This is concise and clear way to define email types.

```java
public enum EmailType {
    REGISTRATION,
    PASSWORD_RESET,
    NEWSLETTER
}
```

These types will be used in mail templates and mail service.

### Mail Template

Mail template allows you to create a template for each email to save time.

You can define templates via `application.yaml` or via beans.

Configuration from `application.yaml` can override bean configuration in case of
conflicting mail types. It allows you to update mail template without needing to
rebuild an application.

```yaml
spring:
  mail:
    username: mailaccount@gmail.com
    password: somepassword
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

You need to have `src/main/resources/templates` directory in your project. This
is default directory for email templates.

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

```java
@Bean
public MailTemplate newsletterTemplate() {
    return new MailTemplate(
            MailType.NEWSLETTER.name(),
            "Our weekly newsletter",
            "newsletter.ftlh"
    );
}
```

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

### Mail Service

`MailService` allows you to use simple methods to send emails.

We have 3 implementations of `MailService` interface. We recommend to use it as
beans and do not instantiate it by itself.

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

`MailService` is using `@Async` annotation to send emails. This will make your
application faster. Average time of sending email is about 5-10 second.

## Usage Examples

#### Send activation email to a new user

```java
@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final MailService mailService;

    /**
     * This method sends registration email to user's email.
     * Service will use provided user's name and email template defined before.
     */
    public void sendActivationEmail(
            final User user
    ) {
        mailService.send(
                MailParameters.builder(
                                user.getUsername(),
                                MailType.ACTIVATION.toString()
                        )
                        .property("name", user.getName())
                        .build()
        );
    }

    /**
     * This method sends registration email to user's email with custom subject.
     * Service will use provided user's name and email template defined before.
     */
    public void sendEmailWithCustomSubject(
            final User user
    ) {
        mailService.send(
                MailParameters.builder(
                                user.getUsername(),
                                MailType.ACTIVATION.toString()
                        )
                        .subject("Welcome, " + user.getName())
                        .property("name", user.getName())
                        .build()
        );
    }
}
```

#### Send email to a batch of users

In this case service will iterate over all provided emails and send email to
each of them. It is convenient to send same emails to many people.

```java
@Service
@RequiredArgsConstructor
public class SomeService {

    private final MailService mailService;

    /**
     * This method sends newsletter email to some emails.
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

    /**
     * This method sends newsletter email to some emails.
     */
    public void sendEmailWithCustomSubject() {
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

## License

This project is licensed under the MIT License - see the [LICENSE](./LICENSE)
file for details.

## Contributing

We welcome contributions! Please feel free to submit issues and enhancement
requests.

To contribute, make a fork and open a pull request. You can find
issues [here](https://github.com/ilyalisov/spring-boot-starter-mail/issues).

Make sure, you follow project's codestyle.
