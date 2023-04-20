package t3h.android.note2.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import t3h.android.note2.dao.NoteDao;
import t3h.android.note2.database.NoteDatabase;
import t3h.android.note2.entity.Note;
import t3h.android.note2.task.DeleteNoteTask;
import t3h.android.note2.task.SaveNoteTask;

public class NoteRepository {
    private final NoteDao noteDao;

    private final Application application;

    public NoteRepository(Application application) {
        this.application = application;
        NoteDatabase db = NoteDatabase.getInstance(application);
        noteDao = db.noteDao();
    }

    public LiveData<List<Note>> getAllNotes() {
        return noteDao.getAllNotes();
    }

    public void saveNote(Note note) {
        SaveNoteTask saveNoteTask = new SaveNoteTask(application);

        if (note.getId() > 0) {
            note.setDateTime(
                    new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault())
                            .format(new Date())
            );
        }

        saveNoteTask.setInputNote(note);
        saveNoteTask.forceLoad();
    }

    public void deleteNote(Note note) {
        DeleteNoteTask deleteNoteTask = new DeleteNoteTask(application);
        deleteNoteTask.setInputNote(note);
        deleteNoteTask.forceLoad();
    }
}
