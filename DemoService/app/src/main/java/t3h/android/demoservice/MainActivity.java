package t3h.android.demoservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.play_music_btn).setOnClickListener(view -> {
            Intent intent = new Intent(this, MainService.class);
            intent.setAction("PLAY_MUSIC");
            startService(intent);
            //stopService(intent);
        });
    }
}