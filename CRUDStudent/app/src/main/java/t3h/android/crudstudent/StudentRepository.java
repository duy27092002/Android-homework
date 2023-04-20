package t3h.android.crudstudent;

import android.app.Application;
import android.widget.EditText;

import androidx.lifecycle.LiveData;

import java.util.List;

public class StudentRepository {
    private StudentDAO mStudentDAO;
    private InsertATL mInsertATL;

    public StudentRepository(Application application) {
        StudentRoomDatabase db = StudentRoomDatabase.getInstance(application);
        mStudentDAO = db.studentDAO();
        mInsertATL = new InsertATL(application);
    }

    public LiveData<List<Student>> getAllStudent() {
        return mStudentDAO.getAll();
    }

    public void insertStudent(Student student) {
        mInsertATL.setInputData(student);
        mInsertATL.forceLoad();
    }

    public void updateStudent(Student student) {

    }

    public void deleteStudent(int uid) {

    }
}
