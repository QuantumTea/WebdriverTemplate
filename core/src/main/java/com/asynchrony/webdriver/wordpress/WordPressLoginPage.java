package com.asynchrony.webdriver.wordpress;

public abstract class WordPressLoginPage
{
    private static final String idUserNameField = "user_login";
    private static final String idPasswordField = "user_pass";
    private static final String idSubmit= "wp-submit";
    private static final String loginPageUri = "wp-login.php";

    public static String getLoginPageUrl(String siteUrl) {
        StringBuilder sb = new StringBuilder();
        sb.append(siteUrl);
        if (!siteUrl.endsWith("/")) {
            sb.append('/');
        }
        sb.append(loginPageUri);
        return sb.toString();
    }

    public static String getSubmitButtonId() {
        return idSubmit;
    }

    public static String getPasswordFieldId() {
        return idPasswordField;
    }

    public static String getUsernameFieldId() {
        return idUserNameField;
    }
}
