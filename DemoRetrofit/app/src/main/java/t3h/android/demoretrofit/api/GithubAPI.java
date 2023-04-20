package t3h.android.demoretrofit.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import t3h.android.demoretrofit.model.User;

public interface GithubAPI {
    @GET("users")
    Call<List<User>> getAllUsers();
}
