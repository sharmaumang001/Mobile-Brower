package com.sharmaumang001.srpbrowser.model;

public class Sites {
    public String url, title;
    public int imageId;

    public Sites(String url, String title, int imageId) {
        this.url = url;
        this.title = title;
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }
}
