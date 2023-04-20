package t3h.android.hellotoast;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CountFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_count, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivityViewModel mMainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        Button buttonCount = view.findViewById(R.id.button_count);
        buttonCount.setOnClickListener(v -> {
            mMainActivityViewModel.increaseValue(1);
        });
    }
}