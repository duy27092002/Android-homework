package t3h.android.mynote.tasks;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import t3h.android.mynote.dao.NoteDao;
import t3h.android.mynote.database.NotesDatabase;
import t3h.android.mynote.entities.Note;

public class SaveNoteTask extends AsyncTaskLoader<Void> {
    private final NoteDao noteDao;
    private Note inputData;

    public SaveNoteTask(@NonNull Context context) {
        super(context);
        NotesDatabase db = NotesDatabase.getInstance(context);
        noteDao = db.noteDao();
    }

    public void setInputData(Note note) {
        inputData = note;
    }

    @Nullable
    @Override
    public Void loadInBackground() {
        noteDao.insertNote(inputData);
        return null;
    }

    @Override
    public void deliverResult(@Nullable Void data) {
        super.deliverResult(data);
    }
}
