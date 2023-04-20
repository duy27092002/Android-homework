package t3h.android.crudstudent;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class StudentViewModel extends AndroidViewModel {
    private StudentRepository mStudentRepo;
    private LiveData<List<Student>> studentList;

    public StudentViewModel(@NonNull Application application) {
        super(application);
        mStudentRepo = new StudentRepository(application);
        studentList = mStudentRepo.getAllStudent();
    }

    public LiveData<List<Student>> getAllStudent() {
        return studentList;
    }

    public void insertStudent(Student student) {
        mStudentRepo.insertStudent(student);
    }

    public void updateStudent(Student student) {
        mStudentRepo.updateStudent(student);
    }

    public void deleteStudent(int uid) {
        mStudentRepo.deleteStudent(uid);
    }
}
