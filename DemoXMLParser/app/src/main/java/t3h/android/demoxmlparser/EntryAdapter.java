package t3h.android.demoxmlparser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.EntryViewHolder> {
    private Context context;
    private List<Entry> entryList;

    public EntryAdapter(Context context, List<Entry> entryList) {
        this.context = context;
        this.entryList = entryList;
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.entry_layout, parent, false);
        return new EntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
        Entry entry = entryList.get(position);
        holder.mTitle.setText(entry.getTitle());
        holder.mAuthorName.setText(entry.getAuthor());
    }

    public class EntryViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle, mAuthorName;

        public EntryViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title);
            mAuthorName = itemView.findViewById(R.id.authorName);
        }
    }

    @Override
    public int getItemCount() {
        return entryList.size();
    }
}
