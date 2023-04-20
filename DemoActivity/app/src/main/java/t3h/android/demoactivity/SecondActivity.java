package t3h.android.demoactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    private static final String LOG_TAG = SecondActivity.class.getSimpleName();
    public static final String EXTRA_REPLY = "This is extra intent reply";
    private EditText mReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.d(LOG_TAG, "-------");
        Log.d(LOG_TAG, "onCreate-SecondActivity");
        mReply = findViewById(R.id.editText_second);
        // nhận intent đã được kích hoạt từ phía MainActivity
        Intent intent = getIntent();
        // lấy message theo extra intent key gửi đến
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        // tham chiếu tới view hiển thị tin nhắn
        TextView textView = findViewById(R.id.text_message);
        // hiển thị tin nhắn (text)
        textView.setText(message);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart-SecondActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume-SecondActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause-SecondActivity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop-SecondActivity");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart-SecondActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy-SecondActivity");
    }

    public void returnReply(View view) {
        // lấy text người dùng nhập vào
        String reply = mReply.getText().toString();
        // tạo intent mới để response intent extra
        Intent replyIntent = new Intent();
        // đưa response message vào intent extra
        replyIntent.putExtra(EXTRA_REPLY, reply);
        // RESULT_OK cho biết rằng response đã thành công
        setResult(RESULT_OK, replyIntent);
        Log.d(LOG_TAG, "End SecondActivity");
        // đóng SecondActivity và trả về MainActivity
        finish();
    }
}