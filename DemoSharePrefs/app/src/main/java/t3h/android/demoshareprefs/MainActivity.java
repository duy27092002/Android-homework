package t3h.android.demoshareprefs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTxtValue;
    private Button mBlackBtn, mBlueBtn, mRedBtn, mGreenBtn, mCountBtn, mResetBtn;
    private MainViewModel mMainViewModel;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTxtValue = findViewById(R.id.count_value);

        mBlackBtn = findViewById(R.id.black_btn);
        mBlackBtn.setOnClickListener(this);

        mRedBtn = findViewById(R.id.red_btn);
        mRedBtn.setOnClickListener(this);

        mBlueBtn = findViewById(R.id.blue_btn);
        mBlueBtn.setOnClickListener(this);

        mGreenBtn = findViewById(R.id.green_btn);
        mGreenBtn.setOnClickListener(this);

        mCountBtn = findViewById(R.id.count_btn);
        mCountBtn.setOnClickListener(this);

        mResetBtn = findViewById(R.id.reset_btn);
        mResetBtn.setOnClickListener(this);

        mMainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        sharedPref = getPreferences(Context.MODE_PRIVATE);

        // load dữ liệu đã lưu khi đóng ứng dụng
        mMainViewModel.setCountValue(sharedPref.getInt("COUNT_VALUE", 0));
        mMainViewModel.setBackgroundColor(sharedPref.getInt("BACKGROUND_COLOR", R.color.gray));

        mMainViewModel.getCountValue().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mTxtValue.setText(integer.toString());
            }
        });

        mMainViewModel.getBackgroundColor().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mTxtValue.setBackgroundResource(integer);
            }
        });

        Log.d("LifeCircle", "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LifeCircle", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LifeCircle", "onResume");
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.black_btn:
                mMainViewModel.setBackgroundColor(R.color.black);
                break;
            case R.id.red_btn:
                mMainViewModel.setBackgroundColor(R.color.red);
                break;
            case R.id.blue_btn:
                mMainViewModel.setBackgroundColor(R.color.blue);
                break;
            case R.id.green_btn:
                mMainViewModel.setBackgroundColor(R.color.green);
                break;
            case R.id.count_btn:
                mMainViewModel.increaseValue(1);
                break;
            case R.id.reset_btn:
                mMainViewModel.resetCountValue();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("COUNT_VALUE", Integer.parseInt(mTxtValue.getText().toString()));
        editor.putInt("BACKGROUND_COLOR", mMainViewModel.getBackgroundColor().getValue());
        editor.apply();
        Log.d("LifeCircle", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LifeCircle", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LifeCircle", "onDestroy");
    }
}