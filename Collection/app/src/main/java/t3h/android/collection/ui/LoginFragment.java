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
import t3h.android.collection.MainActivity;
import t3h.android.collection.R;

public class LoginFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
        TextView signUpText = view.findViewById(R.id.textSignUp);
        signUpText.setOnClickListener(v -> {
            navController.navigate(R.id.signUpFragment);
        });

        TextView forgotPasswordText = view.findViewById(R.id.textForgotPassword);
        forgotPasswordText.setOnClickListener(v -> {
            navController.navigate(R.id.forgotPasswordFragment);
        });

        EditText email = view.findViewById(R.id.email);
        EditText password = view.findViewById(R.id.password);
        ProgressBar loading = view.findViewById(R.id.loading);
        Button loginBtn = view.findViewById(R.id.btnLogin);

        loginBtn.setOnClickListener(v -> {
            if (email.getText().toString().trim().isEmpty() || password.getText().toString().trim().isEmpty()) {
                Toast.makeText(requireActivity(), "Please fill out the form!",
                        Toast.LENGTH_SHORT).show();
            } else {
                loading.setVisibility(View.VISIBLE);
                FirebaseAuthHelper.signIn(email.getText().toString().trim(), password.getText().toString().trim(),
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                loading.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(requireActivity(), "Login successfully!",
                                            Toast.LENGTH_SHORT).show();
                                    requireActivity().onBackPressed();
                                } else {
                                    Toast.makeText(requireActivity(), "Sign in failed!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}