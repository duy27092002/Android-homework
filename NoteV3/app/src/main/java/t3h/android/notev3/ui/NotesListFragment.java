package t3h.android.notev3.ui;

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

import t3h.android.notev3.R;
import t3h.android.notev3.databinding.FragmentNotesListBinding;

public class NotesListFragment extends Fragment {
    private FragmentNotesListBinding fragmentNotesListBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentNotesListBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_notes_list, container, false);
        // Inflate the layout for this fragment
        return fragmentNotesListBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController =
                Navigation.findNavController(requireActivity(), R.id.navHostFragment);
        fragmentNotesListBinding.mainAddNoteBtn.setOnClickListener(v -> {
            navController.navigate(R.id.action_notesListFragment_to_storeOrDetailsNoteFragment);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentNotesListBinding = null;
    }
}