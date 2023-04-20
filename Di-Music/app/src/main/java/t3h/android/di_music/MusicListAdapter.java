package t3h.android.di_music;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MusicViewHolder> {
    public static final int NONE = 0;
    public static final int PLAY = 1;
    public static final int STOP = 2;
    private int mCurrentIndex = -1;
    public int status;
    private List<Song> mData;
    private OnItemClickListener mOnItemClickListener;

    public MusicListAdapter(Context context) {
        mData = new ArrayList<>();
    }

    public void setCurrentIndex(int index) {
        mCurrentIndex = index;
    }

    public void setData(List<Song> musicList) {
        mData = musicList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.music_item, parent, false);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
        holder.bindView(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MusicViewHolder extends RecyclerView.ViewHolder {
        private View mItemView;
        private TextView mTxtSongTitle, mTxtDuration;

        // itemView là view của 1 item trong ds
        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemView = itemView;
            mTxtSongTitle = itemView.findViewById(R.id.txt_song_title);
            mTxtDuration = itemView.findViewById(R.id.txt_duration);
            itemView.setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick(itemView, getAdapterPosition());
                }
            });
        }

        public void bindView(Song song) {
            if(mCurrentIndex == getAdapterPosition()) {
                switch (status) {
                    case STOP:
                        mItemView.setBackgroundColor(Color.parseColor("#ffff00"));
                        break;
                    case PLAY:
                        mItemView.setBackgroundColor(Color.parseColor("#00ff00"));
                        break;
                    case NONE:
                        mItemView.setBackgroundColor(Color.parseColor("#f1f1f1"));
                        break;
                }
            } else {
                mItemView.setBackgroundColor(Color.parseColor("#f1f1f1"));
            }
            mTxtSongTitle.setText(song.getTitle());
            mTxtDuration.setText(song.getFormatTimes());
        }
    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }
}
