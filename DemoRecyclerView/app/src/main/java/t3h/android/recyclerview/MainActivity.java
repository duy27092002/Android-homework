package t3h.android.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    // danh sách sẽ hiển thị
    private final LinkedList<String> mWordList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private WordAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // thêm phần tử vào danh sách
        for (int i = 0; i < 20; i++){
            mWordList.addLast("Word " + i);
        }

        // Tham chiếu tới RecyclerView
        mRecyclerView = findViewById(R.id.rcv_word);
        // Tạo Adapter và cung cấp dữ liệu cần hiển thị
        mAdapter = new WordAdapter(this, mWordList);
        // Kết nối adapter với RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Cung cấp cho RecyclerView 1 trình quản lý bố cục mặc định
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2,
                        StaggeredGridLayoutManager.VERTICAL)
        );

        // Xử lý sự kiện thêm item
        FloatingActionButton floatingActionButton = findViewById(R.id.fab_add);
        floatingActionButton.setOnClickListener(v -> {
            int wordListSize = mWordList.size();
            // Thêm 1 phần tử mới vào cuối danh sách
            mWordList.addLast("+ Word " + wordListSize);
            // Thông báo cho adatper biết về sự thay đổi
            mAdapter.notifyItemInserted(wordListSize);
            //mRecyclerView.getAdapter().notifyItemInserted(wordListSize);
            // Scroll to the bottom.
            mRecyclerView.smoothScrollToPosition(wordListSize);
        });
    }
}