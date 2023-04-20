package t3h.android.collection.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import t3h.android.collection.adapter.ImageListAdapter;
import t3h.android.collection.viewmodel.MainActivityViewModel;
import t3h.android.collection.R;
import t3h.android.collection.model.Image;

public class ImageListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivityViewModel viewModel = new ViewModelProvider(requireActivity())
                .get(MainActivityViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.rcv_image);
        ImageListAdapter adapter = new ImageListAdapter();
        recyclerView.setAdapter(adapter);

        viewModel.getAllImage().observe(getViewLifecycleOwner(), new Observer<List<Image>>() {
            @Override
            public void onChanged(List<Image> imageList) {
                adapter.setData(imageList);
            }
        });

        NavController navController =
                Navigation.findNavController(requireActivity(), R.id.nav_host);
        adapter.setOnItemClickListener(new ImageListAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, Image image, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt(ImageSlideFragment.EXTRA_START_PAGE, position);
                navController
                        .navigate(R.id.action_imageListFragment_to_imageSlideFragment, bundle);
            }
        });
    }
}