package t3h.android.lesson24;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CountValueViewModel extends ViewModel {
    private MutableLiveData<Integer> countValue = new MutableLiveData<>(0);

    public LiveData<Integer> getCountValue() {
        return countValue;
    }

    public void increaseCountValue() {
        countValue.setValue(countValue.getValue() + 1);
    }
}
