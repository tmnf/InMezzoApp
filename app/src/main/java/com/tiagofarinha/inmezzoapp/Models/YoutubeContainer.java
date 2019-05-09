package com.tiagofarinha.inmezzoapp.Models;

public class YoutubeContainer {

    protected String url, id;

    public YoutubeContainer(String url) {
        this.url = url;
        this.id = generateID();
    }

    public YoutubeContainer() {
    }

    private String generateID() {
        String[] split_url;
        String res = "";

        if (url.contains("youtube.com")) {
            split_url = url.split("=");
            res = split_url[1];
        } else if (url.contains("youtu.be")) {
            split_url = url.split("be/");
            res = split_url[1];
        }

        return res;
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }
}
