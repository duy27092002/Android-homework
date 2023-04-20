package t3h.android.note2.adapter;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import t3h.android.note2.R;
import t3h.android.note2.entity.Note;
import t3h.android.note2.listener.NoteListener;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {
    // danh sách note để hiển thị
    private List<Note> noteList;

    // danh sách toàn bộ note
    private List<Note> noteSource;

    private NoteListener noteListener;

    private Timer timer;

    public NoteListAdapter() {
        noteList = new ArrayList<>();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
        noteSource = noteList;
        notifyDataSetChanged();
    }

    public void setNoteListener(NoteListener noteListener) {
        this.noteListener = noteListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note_layout, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bindView(noteList.get(position));
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView dateTime;
        private final TextView subtitle;
        private final ImageView noteImage;

        private final LinearLayout itemNoteLayout;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleText);
            dateTime = itemView.findViewById(R.id.dateTimeText);
            subtitle = itemView.findViewById(R.id.subtitleText);
            noteImage = itemView.findViewById(R.id.noteImage);
            itemNoteLayout = itemView.findViewById(R.id.itemNoteLayout);

            itemView.setOnClickListener(v -> {
                if (noteListener != null) {
                    noteListener.onNoteClicked(noteList.get(getAdapterPosition()));
                }
            });
        }

        public void bindView(Note note) {
            title.setText(note.getTitle());
            dateTime.setText(note.getDateTime());

            String getSubtitle = note.getSubtitle();
            if (!getSubtitle.isEmpty()) {
                subtitle.setText(note.getSubtitle());
            } else {
                subtitle.setVisibility(View.GONE);
            }

            GradientDrawable gradientDrawable = (GradientDrawable) itemNoteLayout.getBackground();
            if (note.getColor() != null) {
                gradientDrawable.setColor(Color.parseColor(note.getColor()));
            } else {
                gradientDrawable.setColor(Color.parseColor("#333333"));
            }

            if (note.getImagePath() != null) {
                noteImage.setImageBitmap(BitmapFactory.decodeFile(note.getImagePath()));
                noteImage.setVisibility(View.VISIBLE);
            } else {
                noteImage.setVisibility(View.GONE);
            }
        }
    }

    public void searchNotes(String keyword) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (keyword.trim().isEmpty()) {
                    noteList = noteSource;
                } else {
                    List<Note> temp = new ArrayList<>();
                    for (Note note : noteSource) {
                        if (note.getTitle().toLowerCase().contains(keyword.toLowerCase())
                                || note.getSubtitle().toLowerCase().contains(keyword.toLowerCase())
                                || note.getContent().toLowerCase().contains(keyword.toLowerCase())) {
                            temp.add(note);
                        }
                    }
                    noteList = temp;
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        }, 500);
    }

    public void cancelTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }
}
