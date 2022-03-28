package com.example.easyteamup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

import com.example.easyteamup.databinding.ActivityChangeProfileBinding;

public class ChangeProfile extends AppCompatActivity {

    private EditText editTextFileName;
    private TextView textViewStatus;
    private ImageView imageView;

    private DatabaseHelper sqliteDBHandler;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        editTextFileName = findViewById(R.id.editTextTextFileName);
        textViewStatus = findViewById(R.id.textViewStatus);
        imageView = findViewById(R.id.imageView);

        try {
            sqliteDBHandler = new DatabaseHelper(this);
            sqLiteDatabase = sqliteDBHandler.getWritableDatabase();
            sqLiteDatabase.execSQL("CREATE TABLE ImageTable(Name TEXT, Image BLOB)");
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void buttonInsert(View view){
        String stringFilePath = Environment.getExternalStorageDirectory().getPath()+"/Download/"+editTextFileName.getText().toString()+".jpeg";
        Bitmap bitmap = BitmapFactory.decodeFile(stringFilePath);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        byte[] bytesImage = byteArrayOutputStream.toByteArray();

        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", "MyImage");
        contentValues.put("Image", bytesImage);
        sqLiteDatabase.insert("ImageTable", null, contentValues);
        textViewStatus.setText("Insert Successful");
    }

    public void buttonUpdate(View view){
        String stringFilePath = Environment.getExternalStorageDirectory().getPath()+"/Download/"+editTextFileName.getText().toString()+".jpeg";
        Bitmap bitmap = BitmapFactory.decodeFile(stringFilePath);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        byte[] bytesImage = byteArrayOutputStream.toByteArray();

        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", "MyImage");
        contentValues.put("Image", bytesImage);

        sqLiteDatabase.update("ImageTable", contentValues, null, null);
        textViewStatus.setText("Update Successful");
    }

    public void buttonDelete(View view){
        if (sqLiteDatabase.delete("ImageTable", "Name=\"MyImage\"", null)>0){
            textViewStatus.setText("Deletion successful");
        }
    }

    public void buttonFetch(View view){
        String stringQuery = "Select Image from ImageTable where Name=\"MyImage\"";
        Cursor cursor = sqLiteDatabase.rawQuery(stringQuery, null);
        try {
            cursor.moveToFirst();
            byte[] bytesImage = cursor.getBlob(0);
            cursor.close();
            Bitmap bitmapImage = BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);
            imageView.setImageBitmap(bitmapImage);
            textViewStatus.setText("Fetch Successful");
        }
        catch (Exception e){
            textViewStatus.setText("ERROR");
        }
    }
}