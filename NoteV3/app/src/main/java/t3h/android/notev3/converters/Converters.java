package t3h.android.notev3.converters;

import androidx.room.TypeConverter;

import java.sql.Timestamp;

public class Converters {
    @TypeConverter
    public static Timestamp fromTimestamp(Long value) {
        return value == null ? null : new Timestamp(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Timestamp date) {
        return date == null ? null : date.getTime();
    }
}
