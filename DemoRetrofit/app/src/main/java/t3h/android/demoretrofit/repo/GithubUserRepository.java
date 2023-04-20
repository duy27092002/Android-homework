package t3h.android.demoretrofit.repo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import t3h.android.demoretrofit.api.GithubAPI;
import t3h.android.demoretrofit.api.GithubApiProvider;
import t3h.android.demoretrofit.model.User;

public class GithubUserRepository {
    public LiveData<List<User>> getAllUsers() {
        MutableLiveData<List<User>> liveData = new MutableLiveData<>(new ArrayList<>());

        GithubAPI githubAPI = GithubApiProvider.getGithubApi();
        githubAPI.getAllUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.code() == 200) {
                    liveData.setValue(response.body());
                } else {

                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
        return liveData;
    }
}
