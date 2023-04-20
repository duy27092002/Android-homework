package t3h.android.crudstudent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentViewHolder> {
    private List<Student> mStudentList;

    public StudentListAdapter() {
        mStudentList = new ArrayList<>();
    }

    public List<Student> getStudentList() {
        return mStudentList;
    }

    public void setStudentList(List<Student> mStudentList) {
        this.mStudentList = mStudentList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item_layout,
                parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        holder.bindView(mStudentList.get(position));
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        private TextView mStudentTxtName;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            mStudentTxtName = itemView.findViewById(R.id.txt_student_name);
        }

        public void bindView(Student student) {
            StringBuilder stringBuilder = new StringBuilder();
            mStudentTxtName.setText(
                    stringBuilder.append(student.getUid())
                            .append(" - ")
                            .append(student.getName())
            );
        }
    }

    @Override
    public int getItemCount() {
        return mStudentList == null ? 0 : mStudentList.size();
    }
}
