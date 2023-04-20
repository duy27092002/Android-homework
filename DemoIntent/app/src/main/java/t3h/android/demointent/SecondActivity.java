package t3h.android.demointent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // lấy intent được gửi từ MainActivity
        Intent intent = getIntent();

        // lấy tin nhắn trong intent thông qua key = "SEND MESSAGE"
        String getMessage = intent.getStringExtra("SEND MESSAGE");

        // tham chiếu đến view muốn hiển thị tin nhắn
        TextView mMessageTxt = findViewById(R.id.message_txt);

        // hiển thị view
        mMessageTxt.setVisibility(View.VISIBLE);

        // hiển thị tin nhắn
        mMessageTxt.setText(getMessage);
    }

    public void replyMessage(View view){
        EditText edtReplyMess = findViewById(R.id.reply_message_edt);

        String getReplyMess = edtReplyMess.getText().toString();

        // tạo intent để truyền dữ liệu
        Intent replyIntent = new Intent();
        replyIntent.putExtra("REPLY MESSAGE", getReplyMess);

        // RESULT_OK cho biết rằng response đã thành công
        setResult(RESULT_OK, replyIntent);

        // đóng SecondActivity và quay lại MainActivity
        finish();
    }
}