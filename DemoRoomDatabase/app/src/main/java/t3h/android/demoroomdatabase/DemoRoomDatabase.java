package t3h.android.demoroomdatabase;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Student.class}, version = 1)
public abstract class DemoRoomDatabase extends RoomDatabase {
    public abstract StudentDAO studentDAO();

    private static DemoRoomDatabase INSTANCE;

    public static DemoRoomDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (DemoRoomDatabase.class) {
                INSTANCE = Room.databaseBuilder(context,
                                DemoRoomDatabase.class,
                                "student_database"
                        )
                        .build();
            }
        }

        return INSTANCE;
    }
}
