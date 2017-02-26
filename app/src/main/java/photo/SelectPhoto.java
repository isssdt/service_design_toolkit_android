package photo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

import poc.servicedesigntoolkit.getpost.R;
import poc.servicedesigntoolkit.getpost.TouchpointDetails;

import static android.R.attr.id;

public class SelectPhoto extends AppCompatActivity implements View.OnClickListener {

    static final int REQUEST_TAKE_PHOTO = 1;
    private static final String TAG = "selectphoto";
    String newPath,sourcepath;
    Uri filePath;
    private Button buttonCamera;
    private Button buttonGallery;
    private Button buttonUpload;
    private ImageView imageView;
    private EditText editTextName;
    private Bitmap bitmap;
    private static final int SELECT_IMAGE = 1;
    private int PICK_IMAGE_REQUEST = 2;
    private String Username,Touchpoint_name;
    String action,channel,imagefinalPath;
    String rating_intent,reaction_intent,comment_intent;
    String expected_unit,channel_desc, actual_time,actual_unit, JourneyName,id_String;
    Integer id,expected_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_photo);

        buttonCamera = (Button) findViewById(R.id.camera);
        buttonGallery = (Button) findViewById(R.id.gallery);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);
        editTextName = (EditText) findViewById(R.id.editText);
        imageView = (ImageView) findViewById(R.id.imageView);

        Bundle extras = getIntent().getExtras();
        Username = (String) extras.get("Username");
        Touchpoint_name = (String ) extras.get("Touchpoint");

        JourneyName = (String) extras.get("JourneyName");
        action = (String) extras.get("Action");
        channel = (String) extras.get("Channel");
        expected_time = (Integer) extras.get("Expected_time");
        expected_unit = (String) extras.get("Expected_unit");
        channel_desc = (String) extras.get("Channel_Desc");
        id = (Integer) extras.get("Id");
        id_String = id.toString();
        rating_intent = (String) extras.get("rating");
        reaction_intent = (String) extras.get("reaction");
        comment_intent = (String) extras.get("comment");
        actual_time = (String) extras.get("Actual_time");
        actual_unit = (String) extras.get("Actual_unit");

        buttonCamera.setOnClickListener(this);
        buttonGallery.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
    }

    /*public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }*/

    private void uploadImage(){
        //String image = getStringImage(bitmap);

        String selectedImagePath;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            selectedImagePath = ImageFilePath.getPath(getApplicationContext(), filePath);
            Intent i = new Intent(SelectPhoto.this, TouchpointDetails.class);
            i.putExtra("Touchpoint",Touchpoint_name);
            i.putExtra("Username",Username);
            i.putExtra("JourneyName",JourneyName);
            i.putExtra("Action",action);
            i.putExtra("Channel",channel);
            i.putExtra("Expected_time",expected_time);
            i.putExtra("Expected_unit",expected_unit);
            i.putExtra("Channel_Desc",channel_desc);
            i.putExtra("Id",id);
            i.putExtra("rating",rating_intent);
            i.putExtra("reaction",reaction_intent);
            i.putExtra("comment",comment_intent);
            i.putExtra("Actual_time",actual_time);
            i.putExtra("Actual_unit",actual_unit);

            i.putExtra("image",selectedImagePath);
            startActivity(i);
        }
        //String image = getStringImage(bitmap);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                sourcepath = ImageFilePath.getPath(getApplicationContext(), filePath);
            }
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                imageView.setImageBitmap(bitmap);
                createImageFile();
                copyfile();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.gallery:
                showFileChooser();
                break;

            case R.id.camera:
                takePhoto();
                break;

            case R.id.buttonUpload:
                uploadImage();
                break;
        }
    }
    private void copyfile(){
        try {
                File source= new File(sourcepath);
                File destination= new File(newPath);
                if (source.exists()) {
                    FileChannel src = new FileInputStream(source).getChannel();
                    FileChannel dst = new FileOutputStream(destination).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            imagefinalPath = newPath;

        } catch (Exception e) {}
    }

    private void takePhoto() {
        dispatchTakePictureIntent();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = Username +"_"+timeStamp;
        String storageDir = Environment.getExternalStorageDirectory() + "/ServiceDesignToolkit";
        File dir = new File(storageDir);
        if (!dir.exists())
            dir.mkdir();

        File image = new File(storageDir + "/" + imageFileName + ".jpg");

        // Save a file: path for use with ACTION_VIEW intents
        newPath = image.getAbsolutePath();
        Log.i(TAG, "photo path = " + newPath);
        return image;
    }
}