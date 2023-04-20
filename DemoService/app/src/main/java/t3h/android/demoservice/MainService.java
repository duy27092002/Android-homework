package t3h.android.demoservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MainService extends Service {
    // xem xét service được tạo mấy lần
    int count = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("DNV", "onCreate");
    }

    // chờ lệnh từ bên ngoài
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (i == 0) {
                    if (intent != null) {
                        String action = intent.getAction();
                        switch (action) {
                            case "PLAY_MUSIC":
                                count++;
                                Log.d("DNV", "onStartCommand " + action + " " + count);
                        }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
        // có cách để xem các service đang chạy trong máy android
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("DNV", "onDestroy");
    }
}
