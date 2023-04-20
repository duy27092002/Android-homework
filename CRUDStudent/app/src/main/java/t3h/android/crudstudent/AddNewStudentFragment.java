package t3h.android.crudstudent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewStudentFragment extends Fragment {
    private EditText mNameEdt, mClassNameEdt, mGenderEdt;
    private Button mInsertBtn;
    private StudentViewModel mStudentViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_new_student, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNameEdt = view.findViewById(R.id.name_edt);
        mClassNameEdt = view.findViewById(R.id.class_name_edt);
        mGenderEdt = view.findViewById(R.id.gender_edt);
        mInsertBtn = view.findViewById(R.id.insert_btn);
    }

    @Override
    public void onResume() {
        super.onResume();

        mInsertBtn.setOnClickListener(v -> {
            String getStudentName = mNameEdt.getText().toString().trim();
            String getClassName = mClassNameEdt.getText().toString().trim();
            String getGender = mGenderEdt.getText().toString().trim();

            if (getStudentName.length() == 0 || getClassName.length() == 0 || getGender.length() == 0) {
                Toast.makeText(getContext(),
                                "Vui lòng nhập đầy đủ thông tin",
                                Toast.LENGTH_LONG)
                        .show();
            } else {
                Student student = new Student();
                student.setName(getStudentName);
                student.setClassName(getClassName);
                student.setGender(getGender);
                student.setStatus(true);

                mStudentViewModel = new ViewModelProvider(this).get(StudentViewModel.class);
                mStudentViewModel.insertStudent(student);

                mNameEdt.setText("");
                mClassNameEdt.setText("");
                mGenderEdt.setText("");
            }
        });
    }
}