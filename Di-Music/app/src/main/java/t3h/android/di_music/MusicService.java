package t3h.android.di_music;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MusicService extends Service {
    private MusicController mMusicController;
    private LiveData<List<Song>> mSongList;
    RemoteViews mNotificationLayout;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("DNV", "onCreate: service");

        // lấy danh sách nhạc
        MusicRepository musicRepository = new MusicRepository(getApplicationContext());
        mSongList = musicRepository.getAllMusic();

        // sử dụng MusicController
        mMusicController = new MusicController(this, new MusicController.MusicSource() {
            @Override
            public int getSize() {
                return mSongList.getValue().size();
            }

            @Override
            public Song getAtIndex(int index) {
                return mSongList.getValue().get(index);
            }
        });

        // notification
        createNotificationChannel();

        mNotificationLayout
                = new RemoteViews(getPackageName(), R.layout.small_notification_layout);
        mNotificationLayout
                .setTextViewText(R.id.btn_control, "Pause");

        Intent intent = new Intent(this, MusicService.class);
        intent.setAction("PLAY_OR_PAUSE");
        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            pendingIntent = PendingIntent.getForegroundService(
                    this,
                    999,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE);
        }
        mNotificationLayout.setOnClickPendingIntent(R.id.btn_control, pendingIntent);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Demo channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel =
                    new NotificationChannel("demo_channel_id", name, importance);
            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void playSongAt(int index) {
        mMusicController.playSongAt(this, index);
    }

    public void pause() {
        mMusicController.pause();
    }

    public LiveData<List<Song>> getMusicList() {
        return mSongList;
    }

    // start unbound service
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            if (intent.getAction() != null) {
                switch (intent.getAction()) {
                    case "PLAY_OR_PAUSE":
                        if (mMusicController.isPlaying()) {
                            pause();
                            mNotificationLayout.setTextViewText(R.id.btn_control, "Play");
                        } else {
                            if (mMusicController.getCurrentIndex() >= 0) {
                                mMusicController.start();
                                mNotificationLayout.setTextViewText(R.id.btn_control, "Pause");
                            }
                        }
                        break;
                }
            }
        }
        startForeground(999, createNotification());
        return super.onStartCommand(intent, flags, startId);
    }

    private Notification createNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, "demo_channel_id")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setCustomContentView(mNotificationLayout)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        return builder.build();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MusicBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("DNV", "onDestroy: service");
    }

    public class MusicBinder extends Binder {
        public void playSongAt(int index) {
            MusicService.this.playSongAt(index);
        }

        public void pause() {
            MusicService.this.pause();
        }

        public LiveData<List<Song>> getMusics() {
            return mSongList;
        }

        public MusicController getMusicController() {
            return mMusicController;
        }
    }
}
