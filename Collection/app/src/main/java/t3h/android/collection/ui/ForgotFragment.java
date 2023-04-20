package t3h.android.collection.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class ForgotFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText email = view.findViewById(R.id.email);
        ProgressBar loading = view.findViewById(R.id.loading);
        Button sendResetPasswordBtn = view.findViewById(R.id.sendResetPasswordBtn);
        sendResetPasswordBtn.setOnClickListener(v -> {
            loading.setVisibility(View.VISIBLE);
            FirebaseAuthHelper.forgotPassword(email.getText().toString().trim(), new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    loading.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Toast.makeText(requireActivity(),
                                "We send a link to your email. You can use it to change your password",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireActivity(), task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        });
    }
}