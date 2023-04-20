package t3h.android.collection.repo;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import t3h.android.collection.data.ImageHelper;
import t3h.android.collection.model.Image;

public class ImageRepository {
    public LiveData<List<Image>> getAllImage(Context context) {
        MutableLiveData<List<Image>> data = new MutableLiveData<>(new ArrayList<>());
        ImageHelper imageHelper = new ImageHelper();
        imageHelper.loadAllImage(context, new ImageHelper.Callback() {
            @Override
            public void onFinish(List<Image> imageList) {
                data.setValue(imageList);
            }
        });
        return data;
    }
}
