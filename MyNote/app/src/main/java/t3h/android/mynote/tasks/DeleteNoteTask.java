package t3h.android.mynote.tasks;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import t3h.android.mynote.dao.NoteDao;
import t3h.android.mynote.database.NotesDatabase;
import t3h.android.mynote.entities.Note;

public class DeleteNoteTask extends AsyncTaskLoader<Void> {
    private NoteDao noteDao;
    private Note note;

    public DeleteNoteTask(@NonNull Context context) {
        super(context);
        NotesDatabase db = NotesDatabase.getInstance(context);
        noteDao = db.noteDao();
    }

    public void setNote(Note note) {
        this.note = note;
    }

    @Nullable
    @Override
    public Void loadInBackground() {
        noteDao.deleteNote(note);
        return null;
    }

    @Override
    public void deliverResult(@Nullable Void data) {
        super.deliverResult(data);
    }
}
