package t3h.android.demoroomdatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private LiveData<List<Student>> mData;
    private StudentRepository mStudentRepository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        mStudentRepository = new StudentRepository(application);
        mData = mStudentRepository.getAllStudent();
    }

    public LiveData<List<Student>> getAll() {
        return mData;
    }
}
