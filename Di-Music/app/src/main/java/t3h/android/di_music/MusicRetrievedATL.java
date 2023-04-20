package t3h.android.di_music;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

public class MusicRetrievedATL extends AsyncTaskLoader<List<Song>> {
    public interface OnFinishRetrieved{
        void onFinish(List<Song> data);
    }

    private OnFinishRetrieved mOnFinishRetrieved;

    public MusicRetrievedATL(@NonNull Context context, OnFinishRetrieved onFinishRetrieved) {
        super(context);
        mOnFinishRetrieved = onFinishRetrieved;
    }

    @Nullable
    @Override
    public List<Song> loadInBackground() {
        List<Song> songList = new ArrayList<>();

        if (getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            throw new SecurityException("Missing READ_EXTERNAL_STORAGE permission");
        }

        String[] projects = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.DURATION
        };

        ContentResolver contentResolver = getContext().getContentResolver();
        Cursor cursor = contentResolver.query(
          MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
          projects,
          MediaStore.Audio.Media.IS_MUSIC + " = 1",
                null,
                MediaStore.Audio.Media.TITLE + " ASC"
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int idIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
                int titleIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
                int artistIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
                int albumIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM);
                int durationIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
                int albumIdIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID);

                long id = cursor.getLong(idIndex);
                String title = cursor.getString(titleIndex);
                String artist = cursor.getString(artistIndex);
                String album = cursor.getString(albumIndex);
                long duration = cursor.getLong(durationIndex);

                Uri artUri = Uri.parse("content://media/external/audio/albumart");
                long albumId = cursor.getInt(albumIdIndex);
                Uri thisAlbumUri = ContentUris.withAppendedId(artUri, albumId);

                Song song = new Song(id, title, artist, album, thisAlbumUri, duration);
                songList.add(song);
            }
        }

        return songList;
    }

    @Override
    public void deliverResult(@Nullable List<Song> data) {
        super.deliverResult(data);
        mOnFinishRetrieved.onFinish(data);
    }
}
