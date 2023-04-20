package t3h.android.fragmentandviewmodel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FirstFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // lấy dữ liệu theo key trong bundle
        String getName = requireArguments().getString("name");

        // tham chiếu đến textview trên giao diện fragment_first
        // view ở đây chính là giao diện fragment
        TextView welcomeText = view.findViewById(R.id.welcomeText);

        // set text
        welcomeText.setText("Hello " + getName);



        // khai báo ViewModel
        MainActivityViewModel mainActivityViewModel = new ViewModelProvider(requireActivity())
                .get(MainActivityViewModel.class);

        Button countBtn = view.findViewById(R.id.btnCount);

        // bắt sự kiện click vào button
        countBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityViewModel.increaseValue();
            }
        });
    }
}