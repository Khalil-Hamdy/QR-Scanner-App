package com.khalil.qrscanner.domain.module;

public class ScannedItem {
    private int id;
    private String content;
    private long timestamp;
    private boolean isFavorite;

    public ScannedItem(int id, String content, long timestamp, boolean isFavorite) {
        this.id = id;
        this.content = content;
        this.timestamp = timestamp;
        this.isFavorite = isFavorite;
    }
    public ScannedItem( String content, long timestamp, boolean isFavorite) {
        this.content = content;
        this.timestamp = timestamp;
        this.isFavorite = isFavorite;
    }

    public int getId() { return id; }
    public String getContent() { return content; }
    public long getTimestamp() { return timestamp; }
    public boolean isFavorite() { return isFavorite; }

    public void setId(int id) { this.id = id; }
    public void setContent(String data) { this.content = data; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }
}