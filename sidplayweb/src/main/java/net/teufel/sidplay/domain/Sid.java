package net.teufel.sidplay.domain;

public class Sid {

    private int sidId;
    private String title;
    private String author;
    private String release;

    public int getSidId() {
        return sidId;
    }

    public void setSidId(int sidId) {
        this.sidId = sidId;
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

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }
}
