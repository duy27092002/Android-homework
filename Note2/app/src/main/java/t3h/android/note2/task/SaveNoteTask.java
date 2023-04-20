package t3h.android.note2.task;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import t3h.android.note2.dao.NoteDao;
import t3h.android.note2.database.NoteDatabase;
import t3h.android.note2.entity.Note;

public class SaveNoteTask extends AsyncTaskLoader<Void> {
    private NoteDao noteDao;
    private Note inputNote;

    public SaveNoteTask(@NonNull Context context) {
        super(context);
        NoteDatabase db = NoteDatabase.getInstance(context);
        noteDao = db.noteDao();
    }

    public void setInputNote(Note inputNote) {
        this.inputNote = inputNote;
    }

    @Nullable
    @Override
    public Void loadInBackground() {
        noteDao.saveNote(inputNote);
        return null;
    }
}
