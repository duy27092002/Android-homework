package t3h.android.demoretrofit.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import t3h.android.demoretrofit.R;
import t3h.android.demoretrofit.databinding.UserItemBinding;
import t3h.android.demoretrofit.model.User;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {
    private List<User> userList;

    public UserListAdapter() {
        userList = new ArrayList<>();
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,
//                parent, false);
//        return new UserViewHolder(view);
        return new UserViewHolder(UserItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bindView(userList.get(position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        UserItemBinding binding;

        public UserViewHolder(@NonNull UserItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(User user) {
            binding.setUser(user);
        }
    }
}
