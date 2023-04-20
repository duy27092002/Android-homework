package t3h.android.collection.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import t3h.android.collection.FirebaseAuthHelper;
import t3h.android.collection.R;

public class ChangePasswordFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText oldPassword = view.findViewById(R.id.oldPassword);
        EditText newPassword = view.findViewById(R.id.newPassword);
        EditText confirmNewPassword = view.findViewById(R.id.confirmNewPassword);
        ProgressBar loading = view.findViewById(R.id.loading);
        Button changePasswordBtn = view.findViewById(R.id.changePasswordBtn);
        changePasswordBtn.setOnClickListener(v -> {
            if (newPassword.getText().toString().trim().equals(confirmNewPassword.getText().toString().trim())) {
                loading.setVisibility(View.VISIBLE);
                FirebaseAuthHelper.changePassword(oldPassword.getText().toString().trim(), newPassword.getText().toString().trim(),
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                loading.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(requireActivity(), "Change password successfully! You can login again.",
                                            Toast.LENGTH_SHORT).show();
                                    FirebaseAuthHelper.logout();
                                    requireActivity().invalidateOptionsMenu();
                                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
                                    navController.navigate(R.id.imageListFragment);
                                } else {
                                    Toast.makeText(requireActivity(), task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                Toast.makeText(requireActivity(), "Invalid confirm password",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}