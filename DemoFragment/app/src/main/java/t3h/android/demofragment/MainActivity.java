package t3h.android.demofragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // thêm fragment vào Activity
        // đảm bảo fragment chỉ được tạo 1 lần khi bắt đầu phiên
        // khi xảy ra config change thì sẽ không phải tạo lại
        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            //bundle.putString("MESSAGE_KEY", "Hello Duy!");
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view_tag, FirstFragment.class, bundle)
                    .commit();
            // đây là mô hình builder (chấm đến khi nào xong logic)
        }

    }
}