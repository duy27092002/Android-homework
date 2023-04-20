package t3h.android.note2.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import t3h.android.note2.entity.Note;
import t3h.android.note2.repository.NoteRepository;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository noteRepository;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
    }

    public LiveData<List<Note>> getAllNotes() {
        return noteRepository.getAllNotes();
    }

    public void saveNote(Note note) {
        noteRepository.saveNote(note);
    }

    public void deleteNote(Note note) {
        noteRepository.deleteNote(note);
    }

    public void getSearchNotesList(String searchKeyword) {

    }
}
