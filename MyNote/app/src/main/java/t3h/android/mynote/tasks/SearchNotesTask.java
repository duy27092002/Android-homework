package t3h.android.mynote.tasks;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class SearchNotesTask extends AsyncTaskLoader<Void> {
    private String searchKeyword;

    public SearchNotesTask(@NonNull Context context) {
        super(context);
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    @Nullable
    @Override
    public Void loadInBackground() {
        return null;
    }
}
