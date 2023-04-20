package t3h.android.note2.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import t3h.android.note2.entity.Note;

@Dao
public interface NoteDao {
    @Query("select * from notes")
    LiveData<List<Note>> getAllNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveNote(Note note);

    @Delete
    void deleteNote(Note note);
}
