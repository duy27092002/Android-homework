package t3h.android.collection.model;

import java.io.Serializable;

public class Image implements Serializable {
    private long id;
    private String name;
    // duong dan
    private String data;

    public Image() {}

    public Image(long id, String name, String data) {
        this.id = id;
        this.name = name;
        this.data = data;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
