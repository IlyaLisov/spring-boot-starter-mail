package config;

public interface MailTemplateConfig {

    /**
     * Returns HTML template file name for email.
     *
     * @return HTML template file name to be populated
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
     * @return true if email is in HTML format
     */
    boolean isHtml();

}
