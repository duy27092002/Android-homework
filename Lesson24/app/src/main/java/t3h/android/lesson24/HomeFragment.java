package t3h.android.lesson24;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import t3h.android.lesson24.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding homeBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homeBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return homeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);

        homeBinding.openToastCountFragmentBtn.setOnClickListener(v ->
                navController.navigate(R.id.action_homeFragment_to_toastCountFragment)
        );

        homeBinding.openUsingGlideFragmentBtn.setOnClickListener(v ->
                navController.navigate(R.id.action_homeFragment_to_usingGlideFragment)
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        homeBinding = null;
    }
}