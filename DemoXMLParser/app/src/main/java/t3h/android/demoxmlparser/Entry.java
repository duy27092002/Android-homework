package t3h.android.demoxmlparser;

import androidx.annotation.NonNull;

public class Entry {
    private String title;
    private String author;
    private String link;

    public Entry(){}

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @NonNull
    @Override
    public String toString() {
        return "Title = " + title + ", Author = " + author + ", link = " + link;
    }
}
