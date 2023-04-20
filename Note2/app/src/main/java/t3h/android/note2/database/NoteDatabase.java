package t3h.android.note2.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import t3h.android.note2.dao.NoteDao;
import t3h.android.note2.entity.Note;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();

    private static NoteDatabase INSTANCE;

    public static synchronized NoteDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, NoteDatabase.class, "note_db").build();
        }
        return INSTANCE;
    }
}
