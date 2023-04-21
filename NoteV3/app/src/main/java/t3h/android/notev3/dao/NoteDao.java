package t3h.android.notev3.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import t3h.android.notev3.entities.Note;

@Dao
public interface NoteDao {
    @Query("select * from notes order by date_time desc")
    LiveData<List<Note>> getAllNotes();

    @Insert(onConflict = REPLACE)
    void storeNote(Note note);

    @Delete
    void deleteNote(Note note);
}
