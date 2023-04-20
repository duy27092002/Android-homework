package t3h.android.collection.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import t3h.android.collection.R;
import t3h.android.collection.adapter.ImageListAdapter;
import t3h.android.collection.model.Image;
import t3h.android.collection.viewmodel.MainActivityViewModel;

public class ImageSlideFragment extends Fragment {
    public static final String EXTRA_START_PAGE = "t3h.android.collection.extras.START_PAGE";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_slide, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int startPage = requireArguments().getInt(EXTRA_START_PAGE, 0);

        MainActivityViewModel viewModel = new ViewModelProvider(requireActivity())
                .get(MainActivityViewModel.class);

        ViewPager2 viewPager2 = view.findViewById(R.id.viewPage_image);
        ImageListAdapter adapter = new ImageListAdapter();
        adapter.setItemType(ImageListAdapter.TYPE_PAGE_ITEM);
        viewPager2.setAdapter(adapter);

        viewModel.getAllImage().observe(getViewLifecycleOwner(), new Observer<List<Image>>() {
            @Override
            public void onChanged(List<Image> images) {
                adapter.setData(images);
                viewPager2.setCurrentItem(startPage, false);
            }
        });
    }
}