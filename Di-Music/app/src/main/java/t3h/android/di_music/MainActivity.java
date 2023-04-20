package t3h.android.di_music;

import android.Manifest;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 999);
        } else {
            init();
        }

    }
    MusicListAdapter mAdapter;
    private MusicService.MusicBinder mBinder;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("DNV", "onServiceConnected: ");
            mBinder = (MusicService.MusicBinder) iBinder;
            mBinder.getMusics().observe(MainActivity.this, new Observer<List<Song>>() {
                @Override
                public void onChanged(List<Song> songs) {
                    if(mBinder.getMusicController().isPlaying()) {
                        mAdapter.status = MusicListAdapter.PLAY;
                    } else {
                        mAdapter.status = MusicListAdapter.STOP;
                    }
                    mAdapter.setCurrentIndex(mBinder.getMusicController().getCurrentIndex());
                    mAdapter.setData(songs);
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d("DNV", "onServiceDisconnected: ");
        }
    };

    public void init(){
        Log.d("DNV", "init: ");
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.rcv_music_list);
        mAdapter = new MusicListAdapter(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = new Intent(this, MusicService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        }
        bindService(intent, mConnection, Service.BIND_AUTO_CREATE);

        mAdapter.setOnItemClickListener(new MusicListAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                if(position == mBinder.getMusicController().getCurrentIndex()){
                    if(mBinder.getMusicController().isPlaying()) {
                        mAdapter.status = MusicListAdapter.STOP;
                        mBinder.pause();
                        v.setBackgroundColor(Color.YELLOW);
                    } else {
                        mAdapter.status = MusicListAdapter.PLAY;
                        mBinder.getMusicController().start();
                        v.setBackgroundColor(Color.GREEN);
                    }
                } else {
                    mAdapter.status = MusicListAdapter.PLAY;
                    mAdapter.setCurrentIndex(position);
                    mAdapter.notifyItemChanged(mBinder.getMusicController().getCurrentIndex());
                    mBinder.playSongAt(position);
                    v.setBackgroundColor(Color.GREEN);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 999) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                init();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }
}