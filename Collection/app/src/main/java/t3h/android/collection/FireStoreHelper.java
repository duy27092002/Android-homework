package t3h.android.collection;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import t3h.android.collection.model.Image;

public class FireStoreHelper {
    public static void addUser(String uid, String email) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        HashMap<String, Object> user = new HashMap<>();
        user.put("email", email);

        db.collection("UserInfo").document(uid).set(user);
    }

    public static void addImageInfo(Image image) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docs = db.collection("ImageInfo").document();
            String docsPath = docs.getPath();
            docs.set(image).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    HashMap<String, Object> imageMap = new HashMap<>();
                    imageMap.put("image_pref", docs);
                    db.collection("UserInfo").document(firebaseUser.getUid())
                            .collection("ImagePref").add(imageMap)
                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    uploadImage(image);
                                }
                            });
                }
            });
        }
    }

    public static LiveData<List<Image>> getAllImages() {
        MutableLiveData<List<Image>> liveData = new MutableLiveData<>(new ArrayList<>());
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("UserInfo").document(firebaseUser.getUid())
                    .collection("ImagePref")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value,
                                            @Nullable FirebaseFirestoreException error) {
                            if (value != null) {
                                for (DocumentSnapshot document : value.getDocuments()) {
                                    DocumentReference imagePref = (DocumentReference) document.get("image_pref");
                                    imagePref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                int id = task.getResult().get("id", Integer.class);
                                                String data = task.getResult().getString("data");
                                                String name = task.getResult().getString("name");
                                                Image image = new Image(id, name, data);
                                                List<Image> images = liveData.getValue();
                                                images.add(image);
                                                liveData.setValue(images);
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
        }
        return liveData;
    }

    public static void uploadImage(Image image) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            String folderPath = image.getData().substring(0, image.getData().lastIndexOf("/"));
            Log.d("FOLDER_PATH", folderPath);
            storage.getReference(firebaseUser.getUid()).child(folderPath)
                    .putFile(Uri.parse(image.getData()));
        }
    }
}
