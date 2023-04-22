package t3h.android.pro22042023;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        writeFileToInternalStorage("test.txt", "ABCD");
        writeFileToInternalStorage("test_1.txt", "hello.....");

        // liệt kê danh sách file thuộc internal storage
        Log.d("FILE_LIST", Arrays.toString(fileList()));

        readInternalStorage("test.txt");

        writeExternal("external_1.txt", "Hello external!");
        readExternal("external_1.txt");
    }

    @Override
    protected void onResume() {
        super.onResume();

//        try {
//            InputStream inputStream = getAssets().open("hello.txt");
//            int fileSize = inputStream.available();
//            byte[] buffer = new byte[fileSize];
//            inputStream.read(buffer);
//            inputStream.close();
//
//            Log.d("CONTENT", new String(buffer));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }



    }

    // tạo file ở bộ nhớ trong
    private void writeFileToInternalStorage(String fileName, String content){
        try {
            // tạo file test.txt
            FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE);
            // ghi nội dung cho file
            fos.write(content.getBytes());
            // đóng FileOutputStream
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // đọc nội dung file bộ nhớ trong
    private void readInternalStorage(String fileName) {
        try {
            InputStream inputStream = openFileInput(fileName);
            int fileSize = inputStream.available();
            byte[] buffer = new byte[fileSize];
            inputStream.read(buffer);
            inputStream.close();

            Log.d("INTERNAL_FILE", new String(buffer));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // ghi file bộ nhớ ngoài
    private void writeExternal(String fileName, String content) {
        File document = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(document, fileName);
        try{
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.close();
        } catch (IOException io) {
            throw new RuntimeException(io);
        }
    }

    private void readExternal(String fileName) {
        try {
            // Lấy đường dẫn đến thư mục ứng dụng trên External Storage
            File externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);

            // Tạo đối tượng File cho file cần đọc
            File file = new File(externalFilesDir, fileName);

            // Tạo một InputStream để đọc file
            FileInputStream inputStream = new FileInputStream(file);

            // Đọc dữ liệu từ InputStream
            int fileSize = inputStream.available();
            byte[] buffer = new byte[fileSize];
            inputStream.read(buffer);

            // Đóng InputStream
            inputStream.close();

            Log.d("EXTERNAL_FILE", new String(buffer));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}