package com.example.easyteamup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

import com.example.easyteamup.databinding.ActivityChangeProfileBinding;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Change profile page
 * @author Lucy Shi
 */

public class ChangeProfile extends AppCompatActivity {

    EditText etFirstName, etLastName, etEmail, etContactNo;
    ImageView avatar1, avatar2, avatar3, avatar4;
    Button changeProfile;
    private int avatarIdx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        changeProfile = findViewById(R.id.bt_update_profile);

        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etEmail = findViewById(R.id.et_email);
        etContactNo = findViewById(R.id.et_contact_no);

        avatar1 = findViewById(R.id.imageView);
        avatar2 = findViewById(R.id.imageView2);
        avatar3 = findViewById(R.id.imageView3);
        avatar4 = findViewById(R.id.imageView4);

        avatar1.setOnClickListener(this::onImageClicked);
        avatar2.setOnClickListener(this::onImageClicked);
        avatar3.setOnClickListener(this::onImageClicked);
        avatar4.setOnClickListener(this::onImageClicked);
    }

//     Checking if the input in form is valid
    boolean validateInput() {
        if (etFirstName.getText().toString().equals("")) {
            etFirstName.setError("Please Enter First Name");
            return false;
        }
        if (etLastName.getText().toString().equals("")) {
            etLastName.setError("Please Enter Last Name");
            return false;
        }
        if (etEmail.getText().toString().equals("")) {
            etEmail.setError("Please Enter Email");
            return false;
        }
        if (etContactNo.getText().toString().equals("")) {
            etContactNo.setError("Please Enter Contact No");
            return false;
        }

        // checking the proper email format
        if (!isEmailValid(etEmail.getText().toString())) {
            etEmail.setError("Please Enter Valid Email");
            return false;
        }

        return true;
    }

    boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Hook Click Event

    public void performEditProfile (View v) {
        if (validateInput()) {

            // Input is valid, here send data to server

            String firstName = etFirstName.getText().toString();
            String lastName = etLastName.getText().toString();
            String email = etEmail.getText().toString();
            String contactNo = etContactNo.getText().toString();

            // call API now
            ((MyApplication) this.getApplication()).getUser().setFirstName(firstName);
            ((MyApplication) this.getApplication()).getUser().setLastName(lastName);
            ((MyApplication) this.getApplication()).getUser().setEmail(email);

            Toast.makeText(this,"Profile Update Successfully",Toast.LENGTH_SHORT).show();
            changeProfile.setOnClickListener(this::closeOnClick);
        }
    }

    public void onImageClicked(View view){


        switch(view.getId()) {
            case R.id.imageView:
                avatarIdx = 1;
                break;
            case R.id.imageView2:
                avatarIdx = 2;
                break;
            case R.id.imageView3:
                avatarIdx = 3;
                break;
            case R.id.imageView4:
                avatarIdx = 4;
                break;
        }
//
        Toast.makeText(this,"Avatar Updated!",Toast.LENGTH_SHORT).show();
        ((MyApplication) this.getApplication()).getUser().setPhoto(avatarIdx);
        changeProfile.setOnClickListener(this::closeOnClick);
    }

    public void closeOnClick(View v) {
        switchActivity("Home");
    }

    private void switchActivity(String page) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

}
// public class ChangeProfile extends AppCompatActivity {
//
//    private EditText editTextFileName;
//    private TextView textViewStatus;
//    private ImageView imageView;
//
//    private DatabaseHelper sqliteDBHandler;
//    private SQLiteDatabase sqLiteDatabase;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_change_profile);
//
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
//
//        editTextFileName = findViewById(R.id.editTextTextFileName);
//        textViewStatus = findViewById(R.id.textViewStatus);
//        imageView = findViewById(R.id.imageView);
//
//        try {
//            sqliteDBHandler = new DatabaseHelper(this);
//            sqLiteDatabase = sqliteDBHandler.getWritableDatabase();
//            sqLiteDatabase.execSQL("CREATE TABLE ImageTable(Name TEXT, Image BLOB)");
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
//
//    public void buttonInsert(View view){
//        String stringFilePath = Environment.getExternalStorageDirectory().getPath()+"/Download/"+editTextFileName.getText().toString()+".jpeg";
//        Bitmap bitmap = BitmapFactory.decodeFile(stringFilePath);
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
//        byte[] bytesImage = byteArrayOutputStream.toByteArray();
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("Name", "MyImage");
//        contentValues.put("Image", bytesImage);
//        sqLiteDatabase.insert("ImageTable", null, contentValues);
//        textViewStatus.setText("Insert Successful");
//    }
//
//    public void buttonUpdate(View view){
//        String stringFilePath = Environment.getExternalStorageDirectory().getPath()+"/Download/"+editTextFileName.getText().toString()+".jpeg";
//        Bitmap bitmap = BitmapFactory.decodeFile(stringFilePath);
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
//        byte[] bytesImage = byteArrayOutputStream.toByteArray();
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("Name", "MyImage");
//        contentValues.put("Image", bytesImage);
//
//        sqLiteDatabase.update("ImageTable", contentValues, null, null);
//        textViewStatus.setText("Update Successful");
//    }
//
//    public void buttonDelete(View view){
//        if (sqLiteDatabase.delete("ImageTable", "Name=\"MyImage\"", null)>0){
//            textViewStatus.setText("Deletion successful");
//        }
//    }
//
//    public void buttonFetch(View view){
//        String stringQuery = "Select Image from ImageTable where Name=\"MyImage\"";
//        Cursor cursor = sqLiteDatabase.rawQuery(stringQuery, null);
//        try {
//            cursor.moveToFirst();
//            byte[] bytesImage = cursor.getBlob(0);
//            cursor.close();
//            Bitmap bitmapImage = BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);
//            imageView.setImageBitmap(bitmapImage);
//            textViewStatus.setText("Fetch Successful");
//        }
//        catch (Exception e){
//            textViewStatus.setText("ERROR");
//        }
//    }
//}