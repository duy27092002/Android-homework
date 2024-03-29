package t3h.android.demojsoup;

import androidx.annotation.NonNull;

public class News {
    private String title;
    private String link;

    public News() {}

    public News(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        return "Title = " + title + ", Link = " + link;
    }
}
