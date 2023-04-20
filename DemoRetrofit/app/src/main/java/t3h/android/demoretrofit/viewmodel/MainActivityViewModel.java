package t3h.android.demoretrofit.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import t3h.android.demoretrofit.model.User;
import t3h.android.demoretrofit.repo.GithubUserRepository;

public class MainActivityViewModel extends ViewModel {
    private GithubUserRepository githubUserRepository;

    public MainActivityViewModel() {
        githubUserRepository = new GithubUserRepository();
    }

    public LiveData<List<User>> getAllUsers() {
        return githubUserRepository.getAllUsers();
    }
}
