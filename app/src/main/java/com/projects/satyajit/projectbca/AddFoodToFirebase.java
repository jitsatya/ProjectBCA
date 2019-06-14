package com.projects.satyajit.projectbca;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.List;

public class AddFoodToFirebase extends AppCompatActivity {
    private Button addFoodButton;
    private EditText foodName, energy, fat, saturatedfat, monounsaturated_fat, polyfat, transfat, cholesterol, carbs,
            sugar, dietaryfibre, protein,
            sodium, iron, magnesium, zinc;
    private ImageView foodImage,addImagebutton;
    private static int RESULT_LOAD_IMAGE = 1;
    private Uri mImageUri;
    private StorageReference mStoragereference;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_to_firebase);
        addImagebutton = findViewById(R.id.food_image_upload);
        addFoodButton = findViewById(R.id.add_food_item_button);
        foodImage = findViewById(R.id.food_image_upload);
        foodName = findViewById(R.id.food_upload_name);
        energy = findViewById(R.id.energy_field);
        fat = findViewById(R.id.fat_field);
        saturatedfat = findViewById(R.id.Saturated_fatty_acid_field);
        monounsaturated_fat = findViewById(R.id.monounsaturated_fat);
        polyfat = findViewById(R.id.polyunsaturated_fat_field);
        transfat = findViewById(R.id.Trans_fatty_acid_field);
        cholesterol = findViewById(R.id.cholesterol_field);
        carbs = findViewById(R.id.carbohydrate_field);
        sugar = findViewById(R.id.sugar_field);
        dietaryfibre = findViewById(R.id.dietary_fibre_field);
        protein = findViewById(R.id.protein_field);
        sodium = findViewById(R.id.sodium_field);
        iron = findViewById(R.id.iron_field);
        magnesium = findViewById(R.id.magnesium_field);
        zinc = findViewById(R.id.zinc_field);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        UserId = user.getUid();
        // Specify the folder to add the image.
        mStoragereference = FirebaseStorage.getInstance().getReference("Uploads");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("FoodDataBase");
        addImagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openFileChooser();
            }
        });

        addFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFood();
            }
        });

    }



    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(foodImage);
        }
    }
    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFood(){

        if (mImageUri != null){
            final StorageReference fileReference = mStoragereference.child(System.currentTimeMillis()+ "." + getFileExtension(mImageUri));
            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override

                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(AddFoodToFirebase.this, "Uploaded Successfully, Thank You!", Toast.LENGTH_LONG).show();
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();
                                    FoodItemToFirebase foodItemToFirebase = new FoodItemToFirebase(foodName.getText().toString().trim(), imageUrl,
                                            energy.getText().toString().trim(), fat.getText().toString().trim(), carbs.getText().toString().trim(), protein.getText().toString().trim());
                                    foodItemToFirebase.setSaturatedFat(saturatedfat.getText().toString().trim());
                                    foodItemToFirebase.setMonounsaturatedfat(monounsaturated_fat.getText().toString().trim());
                                    foodItemToFirebase.setPolyUnsaturatedFat(polyfat.getText().toString().trim());
                                    foodItemToFirebase.setTransFat(transfat.getText().toString().trim());
                                    foodItemToFirebase.setCholesterol(cholesterol.getText().toString().trim());
                                    foodItemToFirebase.setSugar(sugar.getText().toString().trim());
                                    foodItemToFirebase.setDietaryFibre(dietaryfibre.getText().toString().trim());
                                    foodItemToFirebase.setSodium(sodium.getText().toString().trim());
                                    foodItemToFirebase.setIron(iron.getText().toString().trim());
                                    foodItemToFirebase.setMagnesium(magnesium.getText().toString().trim());
                                    foodItemToFirebase.setZinc(zinc.getText().toString().trim());

                                    String uploadId = mDatabaseReference.push().getKey();
                                    foodItemToFirebase.setId(uploadId);
                                    mDatabaseReference.child(uploadId).setValue(foodItemToFirebase);
                                    //Adding to users node
                                    UserData userData = new UserData();
                                    userData.setFoodItemToFirebase(foodItemToFirebase);
                                    new FirebaseDatabaseHelper("UserData").upDateUserHistory(UserId,"AddedByUser", userData, new FirebaseDatabaseHelper.UserDataStatus() {
                                        @Override
                                        public void DataIsLoaded(java.util.List<UserData> foods, List<String> keys) {

                                        }

                                        @Override
                                        public void DataIsInserted() {

                                        }

                                        @Override
                                        public void DataIsUpdated() {
                                            Toast.makeText(AddFoodToFirebase.this, "Added to Users Node", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddFoodToFirebase.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                }
            });
        }else{
            Toast.makeText(this, "Please provide an Image", Toast.LENGTH_SHORT).show();
        }

    }
}

