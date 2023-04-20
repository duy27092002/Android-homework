package t3h.android.hellotoast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
//    private int mCount = 0;
//    private TextView mShowCount;

    private MainActivityViewModel mMainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // chỉ lấy ra ViewModel đã tồn tại
        // nếu chưa tồn tại thì tạo mới
        // đảm bảo ViewModel chỉ được tạo 1 lần duy nhất
        mMainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

//        mShowCount = findViewById(R.id.show_count);
//        mShowCount.setText(Integer.toString(mMainActivityViewModel.getValue().getValue()));
        // lắng nghe sự thay đổi giá trị và cập nhật giao diện
        // this là MainActivity
        // khi MainActivity bị hủy đi thì việc đăng ký lắng nghe sự kiện cũng sẽ bị hủy
        // đảm bảo không tràn bộ nhớ
//        mMainActivityViewModel.getValue().observe(this, new Observer<Integer>() {
//            // cập nhật giao diện
//            @Override
//            public void onChanged(Integer integer) {
//               mShowCount.setText(String.valueOf(integer));
//            }
//        });
    }

    public void showToast(View view){
        Toast.makeText(this, R.string.toast_message, Toast.LENGTH_LONG).show();
    }

//    public void countUp(View view){
////        // thay đổi giá trị
////        mCount++;
////        // cập nhật giao diện
////        if (mShowCount != null){
////            mShowCount.setText(Integer.toString(mMainActivityViewModel.getValue().getValue()));
////        }
//
//        mMainActivityViewModel.increaseValue(1);
//    }
}