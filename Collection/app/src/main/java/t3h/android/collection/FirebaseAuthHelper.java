package t3h.android.collection;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseAuthHelper {
    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    // OnCompleteListener lang nghe su kien khi sign up xong (thanh cong or that bai)
    public static void signUp(String email, String password
            , OnCompleteListener<AuthResult> listener) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(listener)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("USERID", task.getResult().getUser().getUid());
                        FireStoreHelper.addUser(task.getResult().getUser().getUid(),
                                task.getResult().getUser().getEmail());
                    }
                });
    }

    public static void signIn(String email, String password
            , OnCompleteListener<AuthResult> listener) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(listener);
    }

    public static void changePassword(String oldPassword, String newPassword
            , OnCompleteListener<Void> listener) {
        FirebaseUser firebaseUser = getCurrentUser();

        AuthCredential credential = EmailAuthProvider
                .getCredential(firebaseUser.getEmail(), oldPassword);
        // Prompt the user to re-provide their sign-in credentials
        firebaseUser.reauthenticate(credential).addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        firebaseUser.updatePassword(newPassword)
                                .addOnCompleteListener(listener);
                    } else {
                        listener.onComplete(task);
                    }
                });
    }

    public static void forgotPassword(String email, OnCompleteListener<Void> listener) {
        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(listener);
    }

    public static void logout() {
        firebaseAuth.signOut();
    }

    public static FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }
}
