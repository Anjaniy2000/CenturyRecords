package com.example.centuryrecords.Models;

public class NewsModelClass {

    private String title;
    private String author;
    private String url;
    private String imageUrl;
    private String date_time;

    public NewsModelClass(){

    }

    public NewsModelClass(String title, String author, String url, String imageUrl, String date_time) {
        this.title = title;
        this.author = author;
        this.url = url;
        this.imageUrl = imageUrl;
        this.date_time = date_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
}
