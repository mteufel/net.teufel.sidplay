package net.teufel.sidplay.domain;

public class Type {

    private int id;
    private String type;

    public Type(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
