package t3h.android.lesson24;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import t3h.android.lesson24.databinding.FragmentToastCountBinding;

public class ToastCountFragment extends Fragment {
    private FragmentToastCountBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_toast_count, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.showToastBtn.setOnClickListener(v ->
                Toast.makeText(requireActivity(), "Hello Toast", Toast.LENGTH_SHORT).show()
        );

        CountValueViewModel viewModel = new ViewModelProvider(requireActivity()).get(CountValueViewModel.class);

        binding.countBtn.setOnClickListener(v -> {
            viewModel.increaseCountValue();
        });

        viewModel.getCountValue().observe(getViewLifecycleOwner(),
                integer -> binding.setCountValue(String.valueOf(integer))
                );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}