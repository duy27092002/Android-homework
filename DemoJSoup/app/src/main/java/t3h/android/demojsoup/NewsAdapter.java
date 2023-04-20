package t3h.android.demojsoup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<News> mNewsList;
    private OnClickItemListener mOnClickItemListener;

    public NewsAdapter(List<News> newsList) {
        mNewsList = newsList;
    }

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        mOnClickItemListener = onClickItemListener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News news = mNewsList.get(position);
        holder.mTitle.setText(news.getTitle());
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.newsTitle);
            itemView.setOnClickListener(v -> {
                if (mOnClickItemListener != null) {
                    mOnClickItemListener.onClick(itemView, getLayoutPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public interface OnClickItemListener {
        void onClick(View view, int position);
    }
}
