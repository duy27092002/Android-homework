package t3h.android.notev3.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import t3h.android.notev3.dao.NoteDao;
import t3h.android.notev3.entities.Note;

@Database(entities = Note.class, version = 1, exportSchema = false)
public abstract class NoteRoomDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();

    private static NoteRoomDatabase INSTANCE;

    public static synchronized NoteRoomDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context, NoteRoomDatabase.class, "note_db.db").build();
        }
        return INSTANCE;
    }
}
