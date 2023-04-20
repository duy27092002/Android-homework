package t3h.android.note2.task;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import t3h.android.note2.entity.Note;

public class SearchNotesTask {

//    private Timer timer;
//
//    public void cancelTimer() {
//        if (timer != null) {
//            timer.cancel();
//        }
//    }
//
//    public List<Note> searchNotes(String searchKeyword, List<Note> noteSource) {
//        List<Note> noteList = new ArrayList<>();
//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                if (searchKeyword.trim().isEmpty()) {
//                    noteList = noteSource;
//                } else {
//                    List<Note> temp = new ArrayList<>();
//                    for (Note note: noteSource) {
//                        if (note.getTitle().toLowerCase().contains(searchKeyword.toLowerCase())
//                                || note.getSubtitle().toLowerCase().contains(searchKeyword.toLowerCase())
//                                || note.getContent().toLowerCase().contains(searchKeyword.toLowerCase())) {
//                            temp.add(note);
//                        }
//                    }
//                    noteList = temp;
//                }
//                new Handler(Looper.getMainLooper()).post(new Runnable() {
//                    @Override
//                    public void run() {
//                        notifyDataSetChanged();
//                    }
//                });
//            }
//        }, 500);
//        return noteList;
//    }
}
