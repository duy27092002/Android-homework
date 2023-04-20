package t3h.android.demointent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> stringList = new ArrayList<>();
        stringList.add("Item 1");
        stringList.add("Item 2");
        stringList.add("Item 3");
        stringList.add("Item 4");
        stringList.add("Item 5");
        stringList.add("Item 6");
        stringList.add("Item 7");
        stringList.add("Item 8");
        stringList.add("Item 9");
        stringList.add("Item 10");
        stringList.add("Item 11");

        RecyclerView recyclerView = findViewById(R.id.rcv);
        ItemListAdapter itemListAdapter = new ItemListAdapter();
        recyclerView.setAdapter(itemListAdapter);
        // setup cách thức hiển thị của danh sách trên giao diện
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemListAdapter.setStringList(stringList);

        itemListAdapter.setOnItemClickListener(new ItemListAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(MainActivity.this, stringList.get(position), Toast.LENGTH_LONG).show();
            }
        });
    }
}