package com.sharmaumang001.srpbrowser.Model;

public class Sites {
    public String url,title;
    public int imageid;

    public Sites(String url, String title, int imageid) {
        this.url = url;
        this.title = title;
        this.imageid = imageid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
