package t3h.android.di_music;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class MusicRepository {
    private Context mContext;

    public MusicRepository(Context context){
        mContext = context;
    }

    public LiveData<List<Song>> getAllMusic(){
        MutableLiveData<List<Song>> liveData = new MutableLiveData<>(new ArrayList<>());
        MusicRetrievedATL musicRetrieved = new MusicRetrievedATL(mContext, new MusicRetrievedATL.OnFinishRetrieved() {
            @Override
            public void onFinish(List<Song> data) {
                liveData.setValue(data);
            }
        });
        musicRetrieved.forceLoad();
        return liveData;
    }
}
