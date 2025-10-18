package com.halil.ozel.photosapp.data.local;

public class FavoritePhoto {
    private String id;
    private String url;
    private String title;
    private long timestamp;

    public FavoritePhoto(String id, String url, String title) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.timestamp = System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
