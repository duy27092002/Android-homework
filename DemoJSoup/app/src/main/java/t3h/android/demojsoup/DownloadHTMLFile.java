package t3h.android.demojsoup;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class DownloadHTMLFile extends AsyncTaskLoader<Document> {
    private String mUrl;

    private DownloadCompleteListener mDownloadCompleteListener;

    public DownloadHTMLFile(@NonNull Context context) {
        super(context);
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public void setDownloadCompleteListener(DownloadCompleteListener downloadCompleteListener) {
        mDownloadCompleteListener = downloadCompleteListener;
    }

    // chạy ở luồng background
    // kết nối và lấy tài liệu về
    @Nullable
    @Override
    public Document loadInBackground() {
        try {
            return Jsoup.connect(mUrl).userAgent("Mozilla/5.0").get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // chạy ở luồng chính
    // data là dữ liệu lấy được sau khi loadInBackground hoàn thành
    @Override
    public void deliverResult(@Nullable Document data) {
        super.deliverResult(data);
        if (mDownloadCompleteListener != null) {
            mDownloadCompleteListener.onSuccess(data);
        }
    }

    public interface DownloadCompleteListener {
        // lấy dữ liệu cần thiết từ document
        void onSuccess(Document document);
    }
}
