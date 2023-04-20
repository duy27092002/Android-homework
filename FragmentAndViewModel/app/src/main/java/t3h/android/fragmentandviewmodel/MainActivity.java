package t3h.android.fragmentandviewmodel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // đảm bảo fragment được tạo 1 lần duy nhất
        if (savedInstanceState == null) {
            // tạo dữ liệu ban đầu cho FirstFragment
            Bundle bundle = new Bundle();
            bundle.putString("name", "Duy");

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.first_fragment_container_view, FirstFragment.class, bundle)
                    .add(R.id.second_fragment_container_view, SecondFragment.class, null)
                    .commit();
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        TextView showCountValue = findViewById(R.id.countValue);
//
//        MainActivityViewModel mainActivityViewModel = new ViewModelProvider(this)
//                .get(MainActivityViewModel.class);
//
//        // lắng nghe sự thay đổi của giá trị và cập nhật giao diện
//        mainActivityViewModel.getCountValue().observe(this, new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer integer) {
//                showCountValue.setText(integer.toString());
//            }
//        });
//    }
}