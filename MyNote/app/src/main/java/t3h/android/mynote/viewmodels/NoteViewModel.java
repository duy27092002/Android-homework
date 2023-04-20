package t3h.android.mynote.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import t3h.android.mynote.entities.Note;
import t3h.android.mynote.repositories.NoteRepository;

public class NoteViewModel extends AndroidViewModel {
    private LiveData<List<Note>> noteList;
    private final NoteRepository noteRepository;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
        noteList = noteRepository.getAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return noteList;
    }

    public void saveNote(Note note) {
        noteRepository.addNote(note);
    }

    public void deleteNote(Note note) {
        noteRepository.deleteNote(note);
    }
}
