package com.example.class3demo2.model;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;

public class FirebaseModel{
    FirebaseFirestore db;
    FirebaseStorage storage;
    FirebaseAuth auth;

    FirebaseModel(){
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
        storage = FirebaseStorage.getInstance();

    }

    public void getAllStudentsSince(Long since, Model.Listener<List<Student>> callback){
        db.collection(Student.COLLECTION)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Student> list = new LinkedList<>();
                if (task.isSuccessful()){
                    QuerySnapshot jsonsList = task.getResult();
                    for (DocumentSnapshot json: jsonsList){
                        Student st = Student.fromJson(json.getData());
                        list.add(st);
                    }
                }
                callback.onComplete(list);
            }
        });
    }

    public void addStudent(Student st, Model.Listener<Void> listener) {
        db.collection(Student.COLLECTION).document(st.getId()).set(st.toJson())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                listener.onComplete(null);
            }
        });
    }

    public void addDog(Dog dog, Model.Listener<Void> listener) {
        db.collection(Dog.COLLECTION).document(dog.getId()).set(dog.toJson())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        listener.onComplete(null);
                    }
                });
    }

    public void deleteDog(String dogId, Model.Listener<Void> listener) {
        db.collection(Dog.COLLECTION).document(dogId).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                listener.onComplete(null);
            }
        });
    }

    public void updateByField(String dogId,String field, Object value, Model.Listener<Void> listener) {
        db.collection(Dog.COLLECTION).document(dogId).update(field, value, "lastUpdated", java.time.LocalDateTime.now())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        listener.onComplete(null);
                    }
                });
    }
    public void updateDog(Dog dog, Model.Listener<Void> listener) {
        deleteDog(dog.getId(), listener);
        addDog(dog, listener);
    }

    void uploadImage(String folder, String name, Bitmap bitmap, Model.Listener<String> listener){
        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child(folder + "/" + name + ".jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        listener.onComplete(uri.toString());
                    }
                });
            }
        });
    }

    public void registerUser(String name, String email, String password, ImageView img) {
        auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = auth.getCurrentUser();

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build();
                user.updateProfile(profileUpdates);

                // Set profile image URL
                if (img != null) {

                    final String folder = "profileAvatars";

                    img.setDrawingCacheEnabled(true);
                    img.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
                    Model.instance().uploadImage(folder, email, bitmap, url -> {
                        if (url != null) {
                            Uri photoUri = Uri.parse(url);
                            UserProfileChangeRequest profilePictureUpdates = new UserProfileChangeRequest.Builder()
                                    .setPhotoUri(photoUri)
                                    .build();
                            user.updateProfile(profilePictureUpdates);
                        }
                    });
                }
            } else {
                Log.d("AUTH", "failed to Insert user");
            }
        });
    }

    public interface OnLoginCompleteListener {
        void onLoginComplete(boolean success);
    }

    public void loginUser(String email, String password, OnLoginCompleteListener callback) {
        auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            AuthResult authResult = task.getResult();
                            FirebaseUser user = authResult.getUser();
                            Uri photo = user.getPhotoUrl();
                            callback.onLoginComplete(true);
                        } else {
                            Log.d("lotan", "error in login: " + task.getException());
                            callback.onLoginComplete(false);
                        }
                    }
                });
    }

    public User getLoggedInUser() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user == null) return null;

        Uri photo = user.getPhotoUrl();
        String userPhoto = null;
        if (photo != null) {
            userPhoto = photo.toString();
        }

        User loggedInUser = new User(user.getEmail(), user.getDisplayName(), userPhoto);
        return loggedInUser;
    }

    public void updateUserName(String name) {
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();
        user.updateProfile(profileUpdates);
    }

    public void updateUserPhoto(Uri photo) {
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        UserProfileChangeRequest profilePictureUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(photo)
                .build();
        user.updateProfile(profilePictureUpdates);
    }

    public void logout() {
        auth = FirebaseAuth.getInstance();
        auth.signOut();
    }
}
