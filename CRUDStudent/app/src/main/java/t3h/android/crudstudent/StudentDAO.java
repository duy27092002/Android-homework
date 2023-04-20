package t3h.android.crudstudent;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StudentDAO {
    @Query("select * from students where status = 1")
    LiveData<List<Student>> getAll();

    @Insert
    void insert(Student... students);

    @Update
    void update(Student... students);

    @Query("delete from students where uid = :uid")
    void delete(int uid);
}
