package com.example.appbanhangonl.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.appbanhangonl.R;
import com.example.appbanhangonl.model.ToastHelper;
import com.example.appbanhangonl.model.UserModel;
import com.example.appbanhangonl.retrofit.ApiBanHang;
import com.example.appbanhangonl.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextUsername, editTextMobile, editTextImageUser;
    private ImageView imageViewProfilePicture;
    private Button buttonUpdate, buttonLogout, buttonChangePicture;
    private static final int REQUEST_STORAGE_PERMISSION = 100;

    private void Mapping() {
        editTextEmail = findViewById(R.id.editText_Email);
        editTextUsername = findViewById(R.id.editText_Username);
        editTextMobile = findViewById(R.id.editText_Mobile);
        editTextImageUser = findViewById(R.id.editText_ImageUser);
        imageViewProfilePicture = findViewById(R.id.imageView_ProfilePicture);
        buttonUpdate = findViewById(R.id.button_Update);
        buttonLogout = findViewById(R.id.button_Logout);
        buttonChangePicture = findViewById(R.id.button_ChangePicture);
    }

    private void UpdateProfile() {
        buttonUpdate.setOnClickListener(v -> updateUserProfile());
    }

    private void LogoutProfile() {
        buttonLogout.setOnClickListener(v -> logout());
    }

    private void ChangeImageProfile() {
        buttonChangePicture.setOnClickListener(v -> changeProfilePicture());
    }

    private void getDataUserInfo() {
        Intent intent = getIntent();
        String email = intent.getStringExtra("Email");
        String username = intent.getStringExtra("Username");
        String imageUser = intent.getStringExtra("ImageUser");
        String mobile = intent.getStringExtra("Mobile");

        editTextEmail.setText(email);
        editTextUsername.setText(username);
        editTextMobile.setText(mobile);
        editTextImageUser.setText(imageUser);

        // Load image from Firebase Storage using the URL
        Glide.with(getApplicationContext()).load(imageUser).into(imageViewProfilePicture);
    }

    private void updateUserProfile() {
        try {
            String email = editTextEmail.getText().toString();
            String username = editTextUsername.getText().toString();
            String mobile = editTextMobile.getText().toString();
            String str_hinhanh = editTextImageUser.getText().toString().trim();

            ApiBanHang dataClient = Utils.getData();
            Call<Void> call = dataClient.updateUserProfile(email, username, mobile, str_hinhanh);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        // Cập nhật thông tin người dùng vào Realtime Database
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        UserModel user = new UserModel(email, username, mobile, str_hinhanh);
                        databaseReference.child(userId).setValue(user);

                        // Cập nhật thông tin vào biến user_current và lưu vào PaperDB
                        Utils.user_current = user;

                        // Trả kết quả về MainActivity
                        Intent resultIntent = new Intent();
                        setResult(RESULT_OK, resultIntent);
                        finish();

                        ToastHelper.showCustomToast(getApplicationContext(), "Cập nhật thông tin thành công !!!");
                    } else {
                        ToastHelper.showCustomToast(getApplicationContext(), "Cập nhật thông tin thất bại !!!");
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void updateUIAfterUpdateSuccess() {
        editTextEmail.setText(Utils.user_current.getEmail());
        editTextUsername.setText(Utils.user_current.getUsername());
        editTextMobile.setText(Utils.user_current.getMobile());
        editTextImageUser.setText(Utils.user_current.getImageUser());

        Glide.with(getApplicationContext()).load(Utils.user_current.getImageUser()).into(imageViewProfilePicture);
    }

    private void Event() {
        UpdateProfile();
        LogoutProfile();
        getDataUserInfo();
        ChangeImageProfile();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Mapping();
        requestStoragePermission();
        Event();
    }

    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_STORAGE_PERMISSION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissions granted
            } else {
                ToastHelper.showCustomToast(this, "Không có quyền truy cập !!!");
            }
        }
    }

    private void logout() {
        finish();
    }

    private void changeProfilePicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    private String getRealPathFromURI(Uri uri) {
        String result;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            result = uri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private void saveImageToDatabase(Uri imageUri) {
        if (!isDestroyed()) { // Kiểm tra trạng thái của hoạt động
            String imagePath = getRealPathFromURI(imageUri);

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            StorageReference imageRef = storageRef.child("images/" + new File(imagePath).getName());

            UploadTask uploadTask = imageRef.putFile(imageUri);
            uploadTask.addOnSuccessListener(taskSnapshot -> imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                String downloadUrl = uri.toString();
                editTextImageUser.setText(downloadUrl);

                if (!isDestroyed()) { // Kiểm tra trạng thái của hoạt động trước khi sử dụng Glide
                    Glide.with(ProfileActivity.this).load(downloadUrl).into(imageViewProfilePicture);

                    // Cập nhật URL vào Firestore Database
                    updateImageURLInFirestore(downloadUrl);

                    ToastHelper.showCustomToast(ProfileActivity.this, "Cập nhật ảnh đại diện thành công !!!");
                }
            })).addOnFailureListener(exception -> {
                ToastHelper.showCustomToast(ProfileActivity.this, "Cập nhật ảnh đại diện thất bại: " + exception.getMessage());
            });
        }
    }

    private void updateImageURLInFirestore(String downloadUrl) {
        String userIdFromXampp = String.valueOf(Utils.user_current.getId());
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Tìm người dùng với userId và cập nhật URL hình ảnh
        db.collection("users").document(userIdFromXampp)
                .update("imageuser", downloadUrl)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Cập nhật ảnh đại diện vào Firestore thành công!"))
                .addOnFailureListener(e -> Log.e("Firestore", "Cập nhật ảnh đại diện vào Firestore thất bại: " + e.getMessage()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            saveImageToDatabase(selectedImageUri);
        }
    }
}
