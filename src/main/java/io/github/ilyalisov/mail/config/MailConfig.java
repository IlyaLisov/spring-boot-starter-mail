package io.github.ilyalisov.mail.config;

public interface MailConfig {

    /**
     * Returns HTML template file name for email.
     *
     * @return HTML template file name to be populated or null if it is plain
     */
    String getTemplateFileName();

    /**
     * Returns subject of email.
     *
     * @return subject of email
     */
    String getSubject();


    /**
     * Returns true if email is in HTML format.
     *
     * @return true if email is in HTML format, false is plain
     */
    boolean isHtml();

}
