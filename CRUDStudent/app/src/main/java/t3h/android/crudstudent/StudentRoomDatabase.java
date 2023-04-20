package t3h.android.crudstudent;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Student.class}, version = 1)
public abstract class StudentRoomDatabase extends RoomDatabase {
    public abstract StudentDAO studentDAO();

    private static StudentRoomDatabase INSTANCE;

    public static StudentRoomDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (StudentRoomDatabase.class) {
                INSTANCE = Room.databaseBuilder(context, StudentRoomDatabase.class, "student_db.db")
                        .build();
            }
        }
        return INSTANCE;
    }
}
