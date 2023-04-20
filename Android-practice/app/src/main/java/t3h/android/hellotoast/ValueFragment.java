package t3h.android.hellotoast;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ValueFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_value, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // tìm và truy cập vào MainActivityViewModel
        MainActivityViewModel mMainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        TextView textView = view.findViewById(R.id.show_count);
        // đăng ký (quan sát) lắng nghe sự thay đổi giá trị và cập nhật giao diện
        // khi owner bị hủy đi thì việc đăng ký lắng nghe sự kiện cũng sẽ bị hủy
        // đảm bảo không tràn bộ nhớ
        mMainActivityViewModel.getValue().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                textView.setText(integer.toString());
            }
        });
    }
}