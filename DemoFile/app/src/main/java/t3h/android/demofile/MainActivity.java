package t3h.android.demofile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        writeInternal("Hello internal!");
//        readInternal();

        // lay danh sach file trong thu muc files cua bo nho trong
        Log.d("Internal files", Arrays.toString(fileList()));

        // them folder vao thu muc files cua bo nho trong
//        File dir = getFilesDir();
//        File newDir = new File(dir, "Demo folder");
//        newDir.mkdir();

        // ghi file trong thu muc Demo folder
//        File fileInternal = new File(newDir, "hello.txt");
//        try {
//            FileOutputStream fos = new FileOutputStream(fileInternal);
//            fos.write("Hello".getBytes());
//            fos.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        writeExternal("Hello external!");

//        createTempFile("This is the first temp file");
//        readTempFile("first.txt");
        //deleteCacheFile("first.txt");

        File[] externalStorageVolumes =
                ContextCompat.getExternalFilesDirs(getApplicationContext(), null);
        for (File f : externalStorageVolumes) {
            Log.d("DnV", String.valueOf(f));
        }
    }

    // ghi file lưu ở bộ nhớ trong
    public void writeInternal(String content) {
        try {
            FileOutputStream fos = openFileOutput("test.txt", MODE_PRIVATE);
            fos.write(content.getBytes());
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // đọc file ở bộ nhớ trong
    public void readInternal() {
        try {
            // doc file trong folder asset
//            InputStream inputStream = getAssets().open("demo.txt");

            // doc file trong internal storage or external storage
            InputStream inputStream = openFileInput("test.txt");
            int size = inputStream.available(); // kich thuoc file
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            // ket qua duoc luu vao mang byte[] buffer
            String s = new String(buffer);
            Log.d("READ_FILE", s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeExternal(String content) {
        File document = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(document, "external.txt");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // tạo file lưu trữ tạm thời
    public void createTempFile(String content) {
        try {
            // cách này thì tên file sẽ là: firstXXXX.txt
            // với XXXX là thời gian file này được tạo ra
            // cách này sẽ khiến việc thao tác với file trở lên khó khăn hơn
            //File firstTempFile = File.createTempFile("first", ".txt", getCacheDir());

            // cách này có thể tùy chỉnh tên file theo mong muốn
            File cacheDir = getCacheDir();
            File cacheFile = new File(cacheDir, "first.txt");
            FileOutputStream fos = new FileOutputStream(cacheFile);
            fos.write(content.getBytes());
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readTempFile(String fileName) {
        File cacheFile = new File(getCacheDir(), fileName);
        try {
            InputStream inputStream = new FileInputStream(cacheFile);
            int size = inputStream.available();
            byte[] bytes = new byte[size];
            inputStream.read(bytes);
            inputStream.close();
            String content = new String(bytes);
            Log.d("CacheFile", content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCacheFile(String fileName) {
        File cacheFile = new File(getCacheDir(), fileName);
        cacheFile.delete();
    }

    // kiểm dung lượng của bộ nhớ ngoài
    // Checks if a volume containing external storage is available for read and write.
    private boolean isExternalStorageWritable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    // Checks if a volume containing external storage is available to at least read.
    private boolean isExternalStorageReadable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ||
                Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY);
    }

}