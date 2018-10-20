package net.teufel.sidplay.domain;

import java.util.List;

public class SidDetail {

    private int id;
    private String title;
    private String author;
    private String release;
    private String preferredModel;
    private String hvscPath;
    private String fileName;
    private int typeId;
    private String type;
    private String filterString;
    private int noSubtunes;
    private List<Subtune> subtunes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPreferredModel() {
        return preferredModel;
    }

    public void setPreferredModel(String preferredModel) {
        this.preferredModel = preferredModel;
    }

    public String getHvscPath() {
        return hvscPath;
    }

    public void setHvscPath(String hvscPath) {
        this.hvscPath = hvscPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilterString() {
        return filterString;
    }

    public void setFilterString(String filterString) {
        this.filterString = filterString;
    }

    public int getNoSubtunes() {
        return noSubtunes;
    }

    public void setNoSubtunes(int noSubtunes) {
        this.noSubtunes = noSubtunes;
    }

    public List<Subtune> getSubtunes() {
        return subtunes;
    }

    public void setSubtunes(List<Subtune> subtunes) {
        this.subtunes = subtunes;
    }
}
