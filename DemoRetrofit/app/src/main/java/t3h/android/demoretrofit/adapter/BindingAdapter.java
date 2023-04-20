package t3h.android.demoretrofit.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class BindingAdapter {
    @androidx.databinding.BindingAdapter(value = {"url"})
    public static void bindImageView(ImageView imageView, String url) {
        Glide.with(imageView)
                .load(url)
                .centerCrop()
                .into(imageView);
    }
}
