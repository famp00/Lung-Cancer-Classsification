package com.example.lungcancerwithoutml;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorSpace;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.lung_cancer.ml.Model;

//import org.tensorflow.lite.DataType;
//import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import com.example.lungcancerwithoutml.ml.Model;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Classification extends AppCompatActivity {
    public static final int CamerPermissionCode = 101;
    public static final int cameraRequstCode = 102;
    // public static final int PICK_IMAGE = 1;
    Button pickphoto, takephoto, details, home;
    TextView result, confidences;
    ImageView imageView;
    Button picture;
    int imageSize = 224;
    private Uri mImageUri;

    private final int gallery = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classification);
        pickphoto = findViewById(R.id.pickphoto);
        takephoto = findViewById(R.id.takephoto);
        details = findViewById(R.id.prescription);
        home = findViewById(R.id.back);
        result = findViewById(R.id.squamous);
        confidences = findViewById(R.id.normal);
        imageView = findViewById(R.id.image);


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Classification.this, Patient_Details.class);
                startActivity(i);
            }
        });



        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  s = result.getText().toString();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                String name1 = sharedPreferences.getString("saved_name", "default_value");
                String age1 = sharedPreferences.getString("saved_age", "default_value");
                String date1 = sharedPreferences.getString("saved_date", "default_value");

                Patient_Details patient_details = new Patient_Details();
                Intent i = new Intent(Classification.this, prescription.class);
                i.putExtra("Prescription",s);
                i.putExtra("name",name1);
                i.putExtra("age",age1);
                i.putExtra("date",date1);
                startActivity(i);

            }
        });

        pickphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);



            }


        });


        takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch camera if we have permission
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                  
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 5);
                } else {
                    //Request camera permission if we don't have it.
                    Toast.makeText(Classification.this,
                            "Camera Permisssion Required", Toast.LENGTH_LONG).show();
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);

                }
            }
        });
    }

    public void classification(Bitmap image) {

        try {
            Model model = Model.newInstance(getApplicationContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);

            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int[] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());

            int pixel = 0;
            for (int i = 0; i < imageSize; i++) {
                for (int j = 0; j < imageSize; j++) {
                    int val = intValues[pixel++];
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                }

            }

            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            Model.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidence = outputFeature0.getFloatArray();
            int maxPos = -1;
            float maxConfidence = 0;
            for (int i = 0; i < confidence.length; i++) {
                if (confidence[i] > maxConfidence) {
                    maxConfidence = confidence[i];
                    maxPos++;
                }

            }

            String[] classes = {"Squamous", "Adenocercenoma", "Normal"};
           result.setText(classes[maxPos]);




            String s = "";





            for (int i = 0; i < classes.length; i++) {

                s += String.format("%s:%.1f%% \n", classes[i], confidence[i] * 100);



            }
            confidences.setText(s);





            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Image is picked from the gallery

                Uri imageUri = data.getData();
                try {

                    Bitmap image = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    imageView.setImageBitmap(image);
                    image= Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                    int dimension = Math.min(image.getWidth(), image.getHeight());
                    image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                    imageView.setImageBitmap(image);
                    classification(image);
                } catch (Exception e) {
                    e.printStackTrace();

            }


            /*Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap image = BitmapFactory.decodeFile(filePath);

            int dimension = Math.min(image.getWidth(), image.getHeight());
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
            imageView.setImageBitmap(image);

            image= Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
            classification(image);*/



        } else if (requestCode == 5 && resultCode == RESULT_OK && data != null) {
            // Image is taken using the camera
            Bitmap image = (Bitmap) data.getExtras().get("data");

            int dimension = Math.min(image.getWidth(), image.getHeight());
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
            imageView.setImageBitmap(image);

            image= Bitmap.createScaledBitmap(image, imageSize, imageSize, false);





           classification(image);
        }
    }



}


