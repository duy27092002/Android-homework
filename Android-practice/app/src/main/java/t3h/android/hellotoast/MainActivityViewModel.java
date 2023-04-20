package t3h.android.hellotoast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

// kho dữ liệu
public class MainActivityViewModel extends ViewModel {
    // là lớp con của LiveData
    // cho phép giá trị của LiveData có thể thay đổi
    // cung cấp các phương thức để thay đổi giá trị mà không cần phải tạo obj mới
    // cho phép đăng ký theo dõi dữ liệu và tự động nhận thông báo khi dữ liệu thay đổi
    // 0 là giá trị mặc định
    private MutableLiveData<Integer> mValue = new MutableLiveData<>(0);

    // LiveData lưu trữ dữ liệu mValue, và thông báo cho tất cả observer để update giao diện
    // LiveData cho phép lắng nghe sự thay đổi của dữ liệu bên trong nó
    // truyền tải dữ liệu được cập nhật liên tục
    public LiveData<Integer> getValue(){
        return mValue;
    }

    public void increaseValue(int i){
        mValue.setValue(mValue.getValue() + i);
    }
}
