package t3h.android.demoretrofit.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import t3h.android.demoretrofit.R;
import t3h.android.demoretrofit.adapter.UserListAdapter;
import t3h.android.demoretrofit.databinding.ActivityMainBinding;
import t3h.android.demoretrofit.model.User;
import t3h.android.demoretrofit.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        MainActivityViewModel viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        UserListAdapter adapter = new UserListAdapter();
        mainBinding.setUserAdapter(adapter);

        viewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                adapter.setUserList(users);
            }
        });
    }
}