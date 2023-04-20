package t3h.android.collection.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import t3h.android.collection.model.Image;
import t3h.android.collection.repo.ImageRepository;

public class MainActivityViewModel extends AndroidViewModel {
    private ImageRepository imageRepository;
    private final LiveData<List<Image>> imgList;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        imageRepository = new ImageRepository();
        imgList = imageRepository.getAllImage(application);
    }

    public LiveData<List<Image>> getAllImage() {
        return imgList;
    }
}
