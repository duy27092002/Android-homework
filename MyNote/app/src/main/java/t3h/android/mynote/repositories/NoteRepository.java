package t3h.android.mynote.repositories;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import t3h.android.mynote.dao.NoteDao;
import t3h.android.mynote.database.NotesDatabase;
import t3h.android.mynote.entities.Note;
import t3h.android.mynote.tasks.DeleteNoteTask;
import t3h.android.mynote.tasks.SaveNoteTask;

public class NoteRepository {
    private final SaveNoteTask saveNoteTask;
    private final DeleteNoteTask deleteNoteTask;
    private final NoteDao noteDao;
    private final Application application;

    public NoteRepository(Application application) {
        this.application = application;
        NotesDatabase db = NotesDatabase.getInstance(application);
        noteDao = db.noteDao();
        saveNoteTask = new SaveNoteTask(application);
        deleteNoteTask = new DeleteNoteTask(application);
    }

    public LiveData<List<Note>> getAllNotes() {
        return noteDao.getAllNotes();
    }

    public void addNote(Note note) {
        if (Integer.valueOf(note.getId()) != null) {
            note.setDateTime(new SimpleDateFormat(
                    "EEEE, dd MMMM yyyy HH:mm a",
                    Locale.getDefault())
                    .format(new Date()
                    ));
        }
        saveNoteTask.setInputData(note);
        saveNoteTask.forceLoad();
    }

    public void deleteNote(Note note) {
        deleteNoteTask.setNote(note);
        deleteNoteTask.forceLoad();
    }
}
