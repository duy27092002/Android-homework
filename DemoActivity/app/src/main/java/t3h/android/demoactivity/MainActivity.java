package t3h.android.demoactivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static final String EXTRA_MESSAGE = "This is extra intent key";
    private EditText mMessageEditText;
    // xác định kết quả trả về từ 1 Activity
    public static final int TEXT_REQUEST = 1;
    private TextView mReplyHeadTextView;
    private TextView mReplyTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // mở file activity_main để load giao diện
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "-------");
        Log.d(LOG_TAG, "onCreate-MainActivity");
        // tham chiếu tới input view
        mMessageEditText = findViewById(R.id.editText_main);
        // tham chiếu tới header view
        mReplyHeadTextView = findViewById(R.id.text_header_reply);
        // tham chiếu tới view hiển thị tin nhắn nhận được
        mReplyTextView = findViewById(R.id.text_message_reply);

        // Restore the saved state.
        // See onSaveInstanceState() for what gets saved.
        if (savedInstanceState != null) {
            boolean isVisible =
                    savedInstanceState.getBoolean("reply_visible");
            // Show both the header and the message views. If isVisible is
            // false or missing from the bundle, use the default layout.
            if (isVisible) {
                mReplyHeadTextView.setVisibility(View.VISIBLE);
                mReplyTextView.setText(savedInstanceState
                        .getString("reply_text"));
                mReplyTextView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart-MainActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume-MainActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause-MainActivity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop-MainActivity");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart-MainActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy-MainActivity");
    }

    // xử lý sự kiện click button
    public void launchSecondActivity(View view) {
        // hiển thị log ở console
        Log.d(LOG_TAG, "Button clicked!");
        // tạo intent tới SecondActivity
        Intent intent = new Intent(this, SecondActivity.class);
        // lấy text người dùng nhập vào
        String message = mMessageEditText.getText().toString();
        // tạo extra intent
        intent.putExtra(EXTRA_MESSAGE, message);
        // sử dụng khi CHỈ MUỐN KÍCH HOẠT ACTIVITY KHÁC MÀ KHÔNG CẦN LẤY KẾT QUẢ TRẢ VỀ
        //startActivity(intent);
        // sử dụng khi MUỐN KÍCH HOẠT ACTIVITY KHÁC VÀ LẤY KẾT QUẢ TRẢ VỀ
        startActivityForResult(intent, TEXT_REQUEST);
    }

    // xử lý kết quả trả về từ SecondActivity
    // requestCode là 1 số nguyên được sử dụng để xác định kết quả trả về của SecondActivity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // kiểm tra TEXT_REQUEST để xem có đang xử lý đúng intent mong muốn không
        if (requestCode == TEXT_REQUEST) {
            // kiểm tra intent có thành công không
            if (resultCode == RESULT_OK) {
                // lấy tin nhắn theo key của intent extra
                String reply = data.getStringExtra(SecondActivity.EXTRA_REPLY);
                // làm xuất hiện text view
                mReplyHeadTextView.setVisibility(View.VISIBLE);
                // hiển thị text
                mReplyTextView.setText(reply);
                mReplyTextView.setVisibility(View.VISIBLE);
            }
        }
    }

    // xử lý lưu data trước khi Activity bị recreate
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // If the heading is visible, message needs to be saved.
        // Otherwise we're still using default layout.
        if (mReplyHeadTextView.getVisibility() == View.VISIBLE) {
            outState.putBoolean("reply_visible", true);
            outState.putString("reply_text", mReplyTextView.getText().toString());
        }
    }
}
