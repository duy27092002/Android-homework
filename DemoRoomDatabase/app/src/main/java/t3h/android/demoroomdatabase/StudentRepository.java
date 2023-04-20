package t3h.android.demoroomdatabase;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class StudentRepository {
    private StudentDAO mStudentDAO;

    public StudentRepository(Application application) {
        DemoRoomDatabase db = DemoRoomDatabase.getInstance(application);
        mStudentDAO = db.studentDAO();
    }

    public LiveData<List<Student>> getAllStudent() {
        return mStudentDAO.getAll();
    }
}
