package t3h.android.asynctask;

import android.os.AsyncTask;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class SimpleAsyncTask extends AsyncTask<Void, Void, String> {
    // WeakReference là 1 loại tham chiều yếu mà nó không ngăn cản việc giải phóng bộ nhớ
    // của đối tượng được tham chiếu (tức là đối tượng sẽ bị trình gom rác thu lại nếu không
    // còn được giữ bởi bất kỳ tham chiếu mạnh nào khác)
    private WeakReference<TextView> mTextView;

    public SimpleAsyncTask(TextView tv) {
        mTextView = new WeakReference<>(tv);
    }

    @Override
    protected String doInBackground(Void... voids) {
        Random r = new Random();
        int n = r.nextInt(11); // random number from 0 to 10
        int s = n * 200; // time to pause
        // Sleep for the random amount of time
        try {
            Thread.sleep(s);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Awake at last after sleeping for " + s + " milliseconds!";
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        mTextView.get().setText(result);
    }
}
