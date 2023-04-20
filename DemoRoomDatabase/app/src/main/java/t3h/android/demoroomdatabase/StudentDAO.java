package t3h.android.demoroomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StudentDAO {
    @Insert
    public void insert(Student... student);

    @Update
    public void update(Student... student);

    @Query("select * from student where status = 1")
    public LiveData<List<Student>> getAll();

    @Query("delete from student where uid = :uid")
    public void delete(int uid);
}
