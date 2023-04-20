package t3h.android.fragmentandviewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MainActivityViewModel extends AndroidViewModel {
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    // MutableLiveData cho phép thay đổi giá trị của countValue
    // 0 là giá trị mặc định của countValue
    private MutableLiveData<Integer> countValue = new MutableLiveData<>(0);

    // LiveData giúp lắng nghe sự thay đổi giá trị của countValue
    public LiveData<Integer> getCountValue() {
        return countValue;
    }

    // tăng giá trị lên 1 đơn vị
    public void increaseValue() {
        countValue.setValue(countValue.getValue() + 1);
    }
}
