package t3h.android.demoxmlparser;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class SimpleAsyncTask extends AsyncTask<Void, Void, List<Entry>> {
    private List<Entry> entryList;

    @Override
    protected List<Entry> doInBackground(Void... voids) {
        try {
            ConvertData convertData = new ConvertData();
            InputStream inputStream = downloadUrl(
                    "https://stackoverflow.com/feeds/tag?tagnames=android&sort=newest");
            entryList = convertData.parse(inputStream);
            inputStream.close();
        } catch (IOException | XmlPullParserException e) {
            throw new RuntimeException(e);
        }
        return entryList;
    }

    @Override
    protected void onPostExecute(List<Entry> entryList) {
        super.onPostExecute(entryList);
    }

    private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        return conn.getInputStream();
    }
}
