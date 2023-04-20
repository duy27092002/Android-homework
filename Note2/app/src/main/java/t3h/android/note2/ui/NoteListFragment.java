package t3h.android.note2.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import t3h.android.note2.R;
import t3h.android.note2.adapter.NoteListAdapter;
import t3h.android.note2.viewmodel.NoteViewModel;

public class NoteListFragment extends Fragment {
    private final Bundle bundle = new Bundle();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView addNoteButton = view.findViewById(R.id.addNoteButton);
        bundle.putBoolean("isViewOrUpdate", false);
        addNoteButton.setOnClickListener(v -> navigateToStoreAndViewNoteFragment(bundle));

        showNotesList(view);
    }

    private void navigateToStoreAndViewNoteFragment(Bundle bundle) {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
        navController.navigate(R.id.action_noteListFragment_to_storeAndViewNoteFragment, bundle);
    }

    private void showNotesList(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.notesRcv);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        NoteListAdapter adapter = new NoteListAdapter();
        recyclerView.setAdapter(adapter);

        NoteViewModel noteViewModel = new ViewModelProvider(requireActivity()).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(requireActivity(), notes -> adapter.setNoteList(notes));

        handleItemClickListener(adapter);

        showSearchNotes(view, adapter);
    }

    private void handleItemClickListener(NoteListAdapter adapter){
        adapter.setNoteListener((note) -> {
            bundle.putBoolean("isViewOrUpdate", true);
            bundle.putSerializable("noteInfo", note);
            navigateToStoreAndViewNoteFragment(bundle);
        });
    }

    private void showSearchNotes(View view, NoteListAdapter adapter) {
        EditText searchEdt = view.findViewById(R.id.searchKeyword);
        searchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.cancelTimer();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                adapter.searchNotes(editable.toString());
            }
        });
    }
}