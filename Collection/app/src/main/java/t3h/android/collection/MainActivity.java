package t3h.android.collection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.Manifest;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import t3h.android.collection.model.Image;
import t3h.android.collection.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {
    private MainActivityViewModel mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 999);
        } else {
            init();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 999) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                init();
            }
        }
    }

    private void init() {
        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        FirebaseUser firebaseUser = FirebaseAuthHelper.getCurrentUser();
        if (firebaseUser == null) {
            getMenuInflater().inflate(R.menu.no_auth_menu, menu);
        } else {
            getMenuInflater().inflate(R.menu.auth_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host);
        switch (item.getItemId()) {
            case R.id.login:
                navController.navigate(R.id.loginFragment);
                item.setVisible(false);
                return true;
            case R.id.changePasswordAction:
                navController.navigate(R.id.changePasswordFragment);
                return true;
            case R.id.syncImage:
                if (mainActivityViewModel.getAllImage().getValue() != null) {
                    for (Image image : mainActivityViewModel.getAllImage().getValue()) {
                        FireStoreHelper.addImageInfo(image);
                    }
                }
                return true;
            case R.id.logout:
                FirebaseAuthHelper.logout();
                Toast.makeText(this, "Logout successfully!",
                        Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.imageListFragment);
                invalidateOptionsMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}