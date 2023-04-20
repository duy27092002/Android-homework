package t3h.android.demoxmlparser;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // lấy entry list từ background thread
        List<Entry> mEntryList;
        try {
            mEntryList = new SimpleAsyncTask().execute().get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        // recyclerview
        RecyclerView mRecyclerView = findViewById(R.id.entryList);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        EntryAdapter mEntryAdapter = new EntryAdapter(this, mEntryList);
        mRecyclerView.setAdapter(mEntryAdapter);
    }
}