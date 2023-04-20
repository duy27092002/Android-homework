package t3h.android.demoshareprefs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private MutableLiveData<Integer> mCountValue = new MutableLiveData<>(0);
    private MutableLiveData<Integer> mBackgroundColor = new MutableLiveData<>(R.color.gray);

    public void setCountValue(int value) {
        this.mCountValue.setValue(value);
    }

    public LiveData<Integer> getCountValue(){
        return mCountValue;
    }

    public void setBackgroundColor(int colorValue) {
        this.mBackgroundColor.setValue(colorValue);
    }

    public LiveData<Integer> getBackgroundColor(){
        return mBackgroundColor;
    }

    public void increaseValue(int i){
        mCountValue.setValue(getCountValue().getValue() + 1);
    }

    public void resetCountValue(){
        mCountValue.setValue(0);
        mBackgroundColor.setValue(R.color.gray);
    }
}
