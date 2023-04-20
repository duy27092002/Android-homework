package t3h.android.mynote.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import t3h.android.mynote.dao.NoteDao;
import t3h.android.mynote.entities.Note;

@Database(entities = Note.class, version = 1, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();

    private static NotesDatabase INSTANCE;

    public static synchronized NotesDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context,
                    NotesDatabase.class,
                    "notes_db"
            ).build();
        }
        return INSTANCE;
    }
}
