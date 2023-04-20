package t3h.android.crudstudent;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class InsertATL extends AsyncTaskLoader<Void> {
    private StudentDAO mStudentDAO;
    private Student mInputData;

    public InsertATL(@NonNull Context context) {
        super(context);
        StudentRoomDatabase db = StudentRoomDatabase.getInstance(context);
        mStudentDAO = db.studentDAO();
    }

    public void setInputData(Student student) {
        mInputData = student;
    }

    @Nullable
    @Override
    public Void loadInBackground() {
        mStudentDAO.insert(mInputData);
        return null;
    }

    @Override
    public void deliverResult(@Nullable Void data) {
        super.deliverResult(data);
    }
}
