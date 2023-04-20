package t3h.android.mynote.listeners;

import t3h.android.mynote.entities.Note;

public interface NotesListener {
    void onNoteClicked(Note note, int position);
}
