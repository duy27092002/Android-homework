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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import t3h.android.collection.FirebaseAuthHelper;
import t3h.android.collection.R;

public class SignUpFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView loginText = view.findViewById(R.id.textSignIn);
        EditText email = view.findViewById(R.id.email);
        EditText password = view.findViewById(R.id.password);
        EditText confirmPassword = view.findViewById(R.id.confirmPassword);
        ProgressBar loading = view.findViewById(R.id.loading);

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host);

        loginText.setOnClickListener(v -> {
            navController.navigate(R.id.loginFragment);
        });

        Button signUpBtn = view.findViewById(R.id.btnSignUp);
        signUpBtn.setOnClickListener(v -> {
            if (email.getText().toString().trim().isEmpty()) {
                Toast.makeText(requireActivity(), "Email must not be empty!",
                        Toast.LENGTH_SHORT).show();
            } else {
                if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                    loading.setVisibility(View.VISIBLE);
                    FirebaseAuthHelper.signUp(email.getText().toString().trim(),
                            password.getText().toString().trim(),
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    loading.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(requireActivity(), "Sign up successfully!",
                                                Toast.LENGTH_SHORT).show();
                                        requireActivity().onBackPressed();
                                    } else {
                                        Toast.makeText(requireActivity(), task.getException().getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(requireActivity(), "Invalid confirm password!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}