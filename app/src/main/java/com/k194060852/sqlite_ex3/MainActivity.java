package com.k194060852.sqlite_ex3;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.k194060852.sqlite_ex3.databinding.ActivityMainBinding;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    BottomSheetDialog bottomSheetDialog;
    LinearLayout llOpenCamera, llOpenGallery;

    ActivityResultLauncher<Intent> launcher;

    String capture = null;

    DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        createBottomSheetDialog();

        createDb();

        addEvents();

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode() == RESULT_OK && result.getData() != null){
                if(capture.equals("camera")){
                    Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                    binding.imgPhoto.setImageBitmap(bitmap);
                } else if (capture.equals("photo")) {
                    Uri uri = result.getData().getData();
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(uri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        binding.imgPhoto.setImageBitmap(bitmap);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void createDb() {
        db = new DbHelper(MainActivity.this);
    }

    private void addEvents() {
        binding.btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.show();
            }
        });
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Insert data
                db.insertData(
                        binding.edtProductName.getText().toString(),
                        binding.edtDescription.getText().toString(),
                        convertBitmapToByteArray()
                );
            }

            private byte[] convertBitmapToByteArray() {
                BitmapDrawable drawable = (BitmapDrawable) binding.imgPhoto.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                return outputStream.toByteArray();
            }
        });
    }

    private void createBottomSheetDialog() {
        if(bottomSheetDialog == null){
            View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet, null);
            llOpenCamera = view.findViewById(R.id.llOpenCamera);
            llOpenCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    capture = "camera";
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // startActivity(intent);
                    launcher.launch(intent);
                    bottomSheetDialog.dismiss();
                }
            });
            llOpenGallery = view.findViewById(R.id.llOpenGallery);
            llOpenGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    capture = "photo";
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    // startActivity(intent);
                    launcher.launch(intent);
                    bottomSheetDialog.dismiss();
                }
            });
        }
    }
}