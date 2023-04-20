package t3h.android.collection.data;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

import t3h.android.collection.FireStoreHelper;
import t3h.android.collection.model.Image;

public class ImageHelper {
    // phương thức này sẽ được gọi từ bên ngoài khi muốn load ảnh
    public void loadAllImage(Context context, Callback callback){
        new LoadImageAsyncTaskLoader(context, callback).forceLoad();
    }

    // interface này sẽ cung cấp danh sách ảnh sau khi luồng load ảnh đã hoàn thành công việc
    public interface Callback {
        void onFinish(List<Image> images);
    }

    // luồng load ảnh
    private class LoadImageAsyncTaskLoader extends AsyncTaskLoader<List<Image>> {
        private Callback callback;

        public LoadImageAsyncTaskLoader(@NonNull Context context, Callback callback) {
            super(context);
            this.callback = callback;
        }

        @Nullable
        @Override
        public List<Image> loadInBackground() {
            return getAllImages(getContext());
        }

        @Override
        public void deliverResult(@Nullable List<Image> data) {
            super.deliverResult(data);
            // cung cấp danh sách ảnh đã load được cho Callback interface
            if(callback != null){
                callback.onFinish(data);
            }
        }
    }

    // xử lý load ảnh từ thiết bị android
    private List<Image> getAllImages(Context context){
        List<Image> list = new ArrayList<>();

        String[] projection = {
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.TITLE,
                MediaStore.Images.ImageColumns.DATA
        };

        String where = null;
        // kiểm tra xem version của hệ điều hành android hiện tại có >= version 30 hay không
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            where = MediaStore.Images.ImageColumns.IS_TRASHED + " = 0";
        }

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                where,
                null,
                MediaStore.Images.ImageColumns.DATE_ADDED + " DESC"
        );

        if(cursor != null){
            int idIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID);
            int nameIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.TITLE);
            int dataIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA);

            while (cursor.moveToNext()){
                long id = cursor.getLong(idIndex);
                String name = cursor.getString(nameIndex);
                String data = cursor.getString(dataIndex);

                Image image = new Image(id, name, data);
                list.add(image);

                FireStoreHelper.addImageInfo(image);
            }
        }

        return list;
    }
}
