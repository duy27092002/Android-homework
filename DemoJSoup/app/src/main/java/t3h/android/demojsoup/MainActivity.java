package t3h.android.demojsoup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadHTMLFile mDownloadHTMLFile = new DownloadHTMLFile(this);
        mDownloadHTMLFile.setUrl("https://voz.vn/s/chia-se-kien-thuc.104/");
        // bắt đầu tiến trình tải dữ liệu bất đồng bộ dưới nền
        mDownloadHTMLFile.forceLoad();

        mDownloadHTMLFile.setDownloadCompleteListener(document -> {
            List<News> newsList = new ArrayList<>();
            Elements elements = document.select(".structItem-title > a");
            for (Element element : elements) {
                News news = new News();
                news.setTitle(element.text());
                news.setLink("https://voz.vn/" + element.attr("href"));
                newsList.add(news);
            }

            RecyclerView mRecyclerView = findViewById(R.id.newsList);
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(MainActivity.this);
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            NewsAdapter mNewsAdapter = new NewsAdapter(newsList);
            mRecyclerView.setAdapter(mNewsAdapter);

            mNewsAdapter.setOnClickItemListener((view, position) ->
                    Toast.makeText(MainActivity.this,
                    newsList.get(position).getTitle(), Toast.LENGTH_LONG).show());
        });

//        mDownloadHTMLFile.setDownloadCompleteListener(new DownloadHTMLFile.DownloadCompleteListener() {
//            @Override
//            public void onSuccess(Document document) {
//
//            }
//        });
    }
}