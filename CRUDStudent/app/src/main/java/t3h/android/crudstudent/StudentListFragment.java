package t3h.android.crudstudent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class StudentListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private StudentListAdapter mStudentListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        StudentViewModel studentViewModel = new ViewModelProvider(this).get(StudentViewModel.class);
        mRecyclerView = view.findViewById(R.id.student_list_rcv);
        mStudentListAdapter = new StudentListAdapter();
        mRecyclerView.setAdapter(mStudentListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        studentViewModel.getAllStudent().observe(getViewLifecycleOwner(), new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                mStudentListAdapter.setStudentList(students);
            }
        });

        FloatingActionButton mInsertFab = view.findViewById(R.id.add_new_student_fab);
        mInsertFab.setOnClickListener(v -> {
            FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
            supportFragmentManager.beginTransaction()
                    .replace(R.id.host_fragment, AddNewStudentFragment.class, null)
                    .addToBackStack(null).commit();
        });
    }
}