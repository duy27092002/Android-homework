package t3h.android.demoroomdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainActivityViewModel mMainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DemoRoomDatabase demoRoomDatabase = DemoRoomDatabase.getInstance(getApplicationContext());
        Log.d("DnV", demoRoomDatabase.toString());

        mMainActivityViewModel = new MainActivityViewModel(getApplication());
        mMainActivityViewModel.getAll().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                Log.d("Student list", mMainActivityViewModel.getAll().toString());
            }
        });
    }
}