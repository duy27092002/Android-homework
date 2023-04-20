package t3h.android.note2.task;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import t3h.android.note2.dao.NoteDao;
import t3h.android.note2.database.NoteDatabase;
import t3h.android.note2.entity.Note;

public class DeleteNoteTask extends AsyncTaskLoader<Void> {
    private NoteDao noteDao;

    private Note inputNote;

    public DeleteNoteTask(@NonNull Context context) {
        super(context);
        noteDao = NoteDatabase.getInstance(context).noteDao();
    }

    public void setInputNote(Note inputNote) {
        this.inputNote = inputNote;
    }

    @Nullable
    @Override
    public Void loadInBackground() {
        noteDao.deleteNote(inputNote);
        return null;
    }
}
