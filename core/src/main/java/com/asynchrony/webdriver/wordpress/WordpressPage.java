package com.asynchrony.webdriver.wordpress;

public class WordpressPage {
    private String viewUrl;
    private String deleteUrl;
    private String editUrl;

    public WordpressPage(String viewUrl, String deleteUrl) {
        this.viewUrl = viewUrl;
        this.deleteUrl = deleteUrl;
    }

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }

    public String getDeleteUrl() {
        return deleteUrl;
    }

    public void setDeleteUrl(String deleteUrl) {
        this.deleteUrl = deleteUrl;
    }

    public String getEditUrl() {
        return editUrl;
    }

    public void setEditUrl(String editUrl) {
        this.editUrl = editUrl;
    }
}
